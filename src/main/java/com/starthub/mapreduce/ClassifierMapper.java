package com.starthub.mapreduce;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Harrison on 6/7/2018.
 */
public class ClassifierMapper extends Mapper<LongWritable, ClassifierJob, Text, ClassifierFoldResults> {

    @Override
    protected void map(LongWritable key, ClassifierJob value, Context context) throws IOException, InterruptedException {
        try {
            Text resultskey = new Text(Utils.extraZeros(value.key) + "," + (1 + value.fold) + ",'" + value.dataset
                    + "','" + Utils.classifierToString(value.classifier) + "','settings'"); // need to do this before
            // calculation, because weka
            // seems to modify
            // parameters (undocumented)
            // for certain classifiers
            Random random = new Random(value.key);

//            utils.LOG.info("e1");
            System.out.println("I will now run " + value.toString());
            Classifier classifier = value.classifier;

            Instances data = getInstances(value, context);

            // based on CrossValidationSplitResultProducer.doRun(run)

            data.randomize(random);
            if (data.classAttribute().isNominal()) {
                data.stratify(10);
            }

            // Just to make behaviour absolutely consistent with
            // CrossValidationResultProducer
            for (int tempFold = 0; tempFold < value.fold; tempFold++) {
                data.trainCV(10, tempFold, random);
            }

            Instances train = data.trainCV(10, value.fold, random);
            Instances test = data.testCV(10, value.fold);

            classifier.buildClassifier(train);

            Evaluation eval;

            eval = new Evaluation(data);
            eval.evaluateModel(classifier, test);

            ClassifierFoldResults foldResults = new ClassifierFoldResults(eval, train, test);

            // context.write(value,wfs);
            context.write(resultskey, foldResults);
        } catch (Exception ex) {
            Logger.getLogger(ClassifierMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Instances getInstances(ClassifierJob job, Context context) throws IOException, Exception {
        FileSystem fs = FileSystem.get(context.getConfiguration());

        // Path dataset_hdfs =new
        // Path("hdfs://quickstart.cloudera:8020/user/cloudera/testdata.arff");

        // ConverterUtils.DataSource source = new
        // ConverterUtils.DataSource(fs.open(dataset_hdfs));

        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fs.open(job.getLocal()));

        Instances data = source.getDataSet();

        if (data.classIndex() < 0)
            data.setClassIndex(data.numAttributes() - 1);

        return data;
    }
}
