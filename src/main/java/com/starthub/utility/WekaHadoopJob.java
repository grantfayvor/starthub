package com.starthub.utility;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.AggregateableEvaluation;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.Utils;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Harrison on 4/24/2018.
 */
public class WekaHadoopJob {

    private String classifier;
    private String trainingDataPath;
    private String textToClassify;
    private int classIndex;
    private int numOfSplits;
    private String inputPath;
    private String outputPath;
    private double[] evaluatedResult;

    public WekaHadoopJob(String classifier, String trainingDataPath, String textToClassify, int classIndex, int numOfSplits, String inputPath, String outputPath) {
        this.classifier = classifier;
        this.trainingDataPath = trainingDataPath;
        this.textToClassify = textToClassify;
        this.classIndex = classIndex;
        this.numOfSplits = numOfSplits;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public double[] getEvaluatedResult() {
        return evaluatedResult;
    }

    public void run() {
        Configuration conf = new Configuration();
        conf.setInt("Run-num.splits", numOfSplits);
        conf.setStrings("Run.classify", classifier);
        conf.set("io.serializations", "org.apache.hadoop.io.serializer.JavaSerialization," + "org.apache.hadoop.io.serializer.WritableSerialization");

        try {
            Job job = new Job(conf, "WekaHadoopJob");

            job.setJarByClass(WekaHadoopJob.class);
            job.setMapperClass(WekaMapper.class);
            job.setReducerClass(WekaReducer.class);

            // Start with 1
            job.setNumReduceTasks(1);

            // This section sets the values of the <K2, V2>
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(weka.classifiers.lazy.IBk.class);
            job.setOutputValueClass(AggregateableEvaluation.class);
            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));
            job.setInputFormatClass(WekaInputFormat.class);
            job.waitForCompletion(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private class WekaInputFormat extends TextInputFormat {
        /**
         * Takes a JobContext and returns a list of data split into pieces
         * Basically this is a way of handling large data sets. This method allows
         * us to split a large data set into smaller chunks to pass across worker nodes
         * (or in our case, just to make life a little easier and pass the chunks to a single
         * node so that it is not overwhelmed by one large data set)
         *
         * @see org.apache.hadoop.mapreduce.lib.input.FileInputFormat#getSplits
         * (org.apache.hadoop.mapreduce.JobContext)
         */
        public List<InputSplit> getSplits(JobContext job) throws IOException {

            List<InputSplit> splits = new ArrayList<>();
            for (FileStatus file : listStatus(job)) {
                Path path = file.getPath();
                FileSystem fs = path.getFileSystem(job.getConfiguration());

                //number of bytes in this file
                long length = file.getLen();
                BlockLocation[] blkLocations = fs.getFileBlockLocations(file, 0, length);

                // make sure this is actually a valid file
                if (length != 0) {
                    // set the number of splits to make. NOTE: the value can be changed to anything
                    int count = job.getConfiguration().getInt("Run-num.splits", 1);
                    for (int t = 0; t < count; t++) {
                        //split the file and add each chunk to the list
                        splits.add(new FileSplit(path, 0, length, blkLocations[0].getHosts()));
                    }
                } else {
                    // Create empty array for zero length files
                    splits.add(new FileSplit(path, 0, length, new String[0]));
                }
            }
            return splits;
        }
    }

    private class WekaMapper extends Mapper<Object, Text, Text, AggregateableEvaluation> {
        private Instances randData;
        private Classifier cls;
        private AggregateableEvaluation eval;
        private Classifier clsCopy;
        private String numMaps = "10";
        private String classname = "weka.classifiers.lazy.IBk";
        private int seed = 20;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            System.out.println("CURRENT LINE: " + line);

            Configuration conf = new Configuration();
            FileSystem fileSystem = FileSystem.get(conf);

            Path path = new Path(trainingDataPath);

            if (!fileSystem.exists(path)) {
                System.out.println("File does not exists");
                return;
            }

            TaskAttemptID tid = context.getTaskAttemptID();

            Configuration wekaConfig = context.getConfiguration();
            numMaps = wekaConfig.get("Run-num.splits");
            classname = wekaConfig.get("Run.classify");

            String[] splitter = tid.toString().split("_");
            String jobNumber;
            int n = 0;

            if (splitter[4].length() > 0) {
                jobNumber = splitter[4].substring(splitter[4].length() - 1);
                n = Integer.parseInt(jobNumber);
            }

            FileSystem fs = FileSystem.get(wekaConfig);

            System.out.println("PATH: " + path);

            context.setStatus("Reading in the arff file...");
            randData = readArff(fs, path.toString());
            double[] instanceD = new double[randData.numAttributes()];
            instanceD[0] = randData.attribute(0).addStringValue(textToClassify);
            randData.add(new SparseInstance(1.0, instanceD));
            try {
                randData = new DataSetUtil().tokenize(randData, "last", "first", false);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            randData.setClassIndex(classIndex);
            Instance instanceToClassify = randData.remove(randData.numInstances() - 1);
            instanceToClassify.setClassMissing();
            randData.randomize(new Random(seed));
            context.setStatus("Done reading arff! Initializing aggregateable eval...");

            try {
                eval = new AggregateableEvaluation(randData);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            Instances trainInstance = randData.trainCV(Integer.parseInt(numMaps), n);
            Instances testInstance = randData.testCV(Integer.parseInt(numMaps), n);

            String[] opts = new String[3];
            switch (classname) {
                case "weka.classifiers.lazy.IBk":
                    opts[0] = "";
                    opts[1] = "-K";
                    opts[2] = "1";
                    break;
                case "weka.classifiers.trees.J48":
                    opts[0] = "";
                    opts[1] = "-C";
                    opts[2] = "0.25";
                    break;
                default:
                    opts[0] = "";
                    opts[1] = "";
                    opts[2] = "";
                    break;
            }

            try {
                cls = (Classifier) Utils.forName(Classifier.class, classname, opts);
                context.setStatus("Creating the classifier...");
                clsCopy = AbstractClassifier.makeCopy(cls);

                context.setStatus("Training the classifier...");
                clsCopy.buildClassifier(trainInstance);

                context.setStatus("Evaluating the model...");
                evaluatedResult = eval.evaluateModel(clsCopy, testInstance);

                context.setStatus("Complete");
            } catch (Exception e) {
                System.out.println("Debugging starts here!");
                e.printStackTrace();
            }

            context.write(new Text(line), eval);
        }


        /**
         * This method reads in the arff file that is provided to the program.
         * Nothing really special about the way the data is handled.
         *
         * @param fs
         * @param filePath
         * @throws IOException
         * @throws InterruptedException
         */
        public Instances readArff(FileSystem fs, String filePath) throws IOException, InterruptedException {
            ArffLoader.ArffReader arff;
            Instance inst;
            Instances data = null;
            try (DataInputStream dataInputStream = new DataInputStream(fs.open(new Path(filePath)));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream))) {
                arff = new ArffLoader.ArffReader(reader, 100000);
                data = arff.getStructure();
                data.setClassIndex(data.numAttributes() - 1);
                while ((inst = arff.readInstance(data)) != null) {
                    data.add(inst);
                }
                data = new Instances(data);
                if (data.classAttribute().isNominal()) {
                    data.stratify(Integer.parseInt(numMaps));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
    }

    private class WekaReducer extends Reducer<Text, AggregateableEvaluation, Text, IntWritable> {
        private AggregateableEvaluation aggEval;

        /**
         * The reducer method takes all the stratified, cross-validated
         * values from the mappers in a list and uses an aggregatable evaluation to consolidate
         * them.
         */
        public void reduce(Text key, Iterable<AggregateableEvaluation> values, Context context) throws IOException, InterruptedException {
            int sum = 0;

            for (AggregateableEvaluation val : values) {
                System.out.println("IN THE REDUCER!");

                // The first time through, give aggEval a value
                if (sum == 0) {
                    try {
                        aggEval = val;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    aggEval.aggregate(val);
                }

                try {
                    // This is what is taken from the mapper to be aggregated
                    System.out.println("This is the map result");
                    System.out.println(aggEval.toMatrixString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sum += 1;
            }

            // Here is where the typical weka matrix output is generated
            try {
                System.out.println("This is reduce matrix");
                System.out.println(aggEval.toMatrixString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // calculate the duration of the aggregation
            context.write(key, new IntWritable(sum));
        }
    }

}
