package com.starthub.utility;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Harrison on 4/4/2018.
 */
public class ClassifierUtil extends Thread {

    private static IBk classifier;
    private Thread classifierThread;
    private static Trainer trainer = new Trainer(new IBk());
    private static final String KNN_OPTION = "-K 5 -W 0 " +
            "-A weka.core.neighboursearch.LinearNNSearch " +
            "-A weka.core.EuclideanDistance -R first-last";

    public void trainClassifier(String path) throws Exception {
        classifier = trainer.trainClassifier(path);
        classifier.setOptions(Utils.splitOptions(KNN_OPTION));
    }

    public void trainClassifier(Instances instances) throws Exception {
//        classifierThread = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    classifier = trainer.trainClassifier(instances);
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
//        };
//        classifierThread.run();
        classifier = trainer.trainClassifier(instances);
    }

//    public String classify(String text, String trainingDataPath) throws Exception {
//        Instances trainingData = SgmDataSetUtil.convertToArffAndConstructInstance(trainingDataPath, text);
//        trainClassifier(trainingData);
//        trainingData.lastInstance().setClassMissing();
//        double classIndex = classifier.classifyInstance(trainingData.lastInstance());
//        System.out.println("the class index is " + classIndex);
//        return trainer.instances.attribute(trainingData.numAttributes() - 1).value((int) Math.round(classIndex));
//    }

//    public String classify(String text, String trainingDataPath) throws Exception {
//        Instances trainingData = DataSetUtil.constructInstances(trainingDataPath);
//        trainClassifier(trainingData);
//        List<String> attributeValues = null;
//        Attribute body = new Attribute("value", attributeValues);
//        List<String> tagAttributeValues = new ArrayList<>();
//        Collections.list(trainingData.attribute("class").enumerateValues()).forEach(attribute -> tagAttributeValues.add((String) attribute));
//        Attribute tag = new Attribute("class", tagAttributeValues);
//        ArrayList<Attribute> attributes = new ArrayList<>();
//        attributes.add(body);
//        attributes.add(tag);
//        Instances dataSet = new Instances("DataSet", attributes, 1);
//        Instance instance = new SparseInstance(attributes.size());
//        instance.setValue(body, text);
//        dataSet.add(instance);
//        dataSet = new DataSetUtil().tokenize(dataSet, "last", "first", true);
//        dataSet.setClassIndex(dataSet.numAttributes() - 1);
//        ArffSaver saver1 = new ArffSaver(); // this saver code should be removed
//        saver1.setInstances(dataSet);
//        saver1.setFile(new File(trainingDataPath + "/test" + Math.random() + ".arff"));
//        saver1.writeBatch();
//        Instance instanceToClassify = dataSet.lastInstance();
//        instanceToClassify.setClassMissing();
//        double classIndex = classifier.classifyInstance(instanceToClassify);
//        System.out.println("the class index is " + classIndex);
//        return trainer.instances.attribute(dataSet.numAttributes() - 1).value((int) Math.round(classIndex));
//    }

    public String classify(String textToClassify, String trainingDataPath) throws Exception {
        Instances trainingData = DataSetUtil.constructInstancesFromTrainingData();
        double[] instanceD = new double[trainingData.numAttributes()];
        instanceD[0] = trainingData.attribute(0).addStringValue(textToClassify);
        System.out.println(textToClassify);
        trainingData.add(new SparseInstance(1.0, instanceD));
        trainingData = new DataSetUtil().tokenize(trainingData, "last", "first", false);
        trainingData.setClassIndex(0);
        Instance instanceToClassify = trainingData.remove(trainingData.numInstances() - 1);
        instanceToClassify.setClassMissing();
        trainClassifier(trainingData);
        System.out.println("@@ the training data class to use is " +trainingData.classAttribute().name());
        double classIndex = classifier.classifyInstance(instanceToClassify);
        System.out.println("the class index is " + classIndex);
        List<Object> classes = Collections.list(trainingData.attribute("class").enumerateValues());
        return (String) classes.get((int) Math.round(classIndex));
    }
}
