package com.starthub.utility;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.lazy.IBk;
import weka.core.*;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.util.Collections;

/**
 * Created by Harrison on 4/4/2018.
 */
public class ClassifierUtil extends Thread {

    private static IBk classifier;
    private final String TRAINING_DATA_PATH = "C:/starthub/dataset/" + "training-data.arff";
    private static Trainer trainer = new Trainer(new IBk());

    public void trainClassifier(Instances instances) throws Exception {
        AbstractClassifier abstractClassifier = trainer.trainClassifier(instances);
        if(abstractClassifier instanceof IBk) classifier = (IBk) abstractClassifier;
        else {
            System.out.println("abstract classifier is not an instance of IBK");
            classifier = new IBk();
        }
    }

    public void tokenizeDataSet() throws Exception {
        Instances trainingData = DataSetUtil.constructInstancesFromTrainingData(TRAINING_DATA_PATH);
        trainingData = new DataSetUtil().tokenize(trainingData, "last", "first", false);
        ArffSaver saver1 = new ArffSaver(); // this saver code should be removed
        saver1.setInstances(trainingData);
        saver1.setFile(new File("C:/starthub/dataset/training-data-tokenized.arff"));
        saver1.writeBatch();
    }

    public String classify(String textToClassify) throws Exception {
        Instances trainingData = DataSetUtil.constructInstancesFromTrainingData(TRAINING_DATA_PATH);
        double[] instanceD = new double[trainingData.numAttributes()];
        instanceD[0] = trainingData.attribute(0).addStringValue(textToClassify);
        trainingData.add(new SparseInstance(1.0, instanceD));
        trainingData = new DataSetUtil().tokenize(trainingData, "last", "first", false);
        trainingData.setClassIndex(0);
        Instance instanceToClassify = trainingData.remove(trainingData.numInstances() - 1);
        instanceToClassify.setClassMissing();
        trainClassifier(trainingData);
        System.out.println("@@ the training data class to use is " +trainingData.classAttribute().name());
        double classIndex = classifier.classifyInstance(instanceToClassify);
        System.out.println("the class index is " + classIndex);
        return trainingData.classAttribute().value((int) Math.round(classIndex));
    }
}
