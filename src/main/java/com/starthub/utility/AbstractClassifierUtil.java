package com.starthub.utility;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harrison on 5/12/2018.
 */
public abstract class AbstractClassifierUtil implements Runnable{

    private AbstractClassifier classifier;
    private String trainingDataPath;
    private Trainer trainer;
    private Instances trainingData;
    private String textToClassify;

    public AbstractClassifierUtil() {
    }

    public AbstractClassifierUtil(AbstractClassifier classifier) {
        this.classifier = classifier;
    }

    public AbstractClassifierUtil configureTrainer() throws Exception{
        trainer = new Trainer(classifier.getClass().getConstructor().newInstance());
        return this;
    }

    protected void trainClassifier(Instances instances) throws Exception {
        classifier = trainer.trainClassifier(instances);
    }

    public void tokenizeDataSet() throws Exception {
        if(trainingDataPath == null) throw new NullPointerException("training data path is null");
        Instances trainingData = DataSetUtil.constructInstancesFromTrainingData(trainingDataPath);
        trainingData = new DataSetUtil().tokenize(trainingData, "last", "first", false);
        ArffSaver saver1 = new ArffSaver(); // this saver code should be removed
        saver1.setInstances(trainingData);
        saver1.setFile(new File("C:/starthub/dataset/training-data-tokenized.arff"));
        saver1.writeBatch();
    }

    public String classify(String textToClassify) throws Exception {
        Instance instanceToClassify = prepareForClassification(textToClassify, trainingDataPath, 0, 0, "last", "first", false);
        System.out.println("@@ the training data class to use is " +trainingData.classAttribute().name());
        double classIndex = classifier.classifyInstance(instanceToClassify);
        System.out.println("the class index is " + classIndex);
        return trainingData.classAttribute().value((int) Math.round(classIndex));
    }

    public Map<String, Double> distributify(String textToClassify) throws Exception {
        Instance instanceToClassify = prepareForClassification(textToClassify, trainingDataPath, 6, 0, "last", "1-7", false);
        System.out.println("@@ the training data class to use is " + trainingData.classAttribute().name());
        double[] classDistributions = classifier.distributionForInstance(instanceToClassify);
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < classDistributions.length; i++) {
            result.put(trainingData.classAttribute().value(i), classDistributions[i] * 100);
        }
        return result;
    }

    public Instance prepareForClassification(String textToClassify, String trainingDataPath, int attributePosition, int classIndex,
                                             String stnAttributeRange, String vectorizerAttributeRange, boolean useDictionary) throws Exception{
        trainingData = DataSetUtil.constructInstancesFromTrainingData(trainingDataPath);
        double[] instanceD = new double[trainingData.numAttributes()];
        instanceD[attributePosition] = trainingData.attribute(attributePosition).addStringValue(textToClassify);
        trainingData.add(new SparseInstance(1.0, instanceD));
        trainingData = new DataSetUtil().tokenize(trainingData, stnAttributeRange, vectorizerAttributeRange, useDictionary);
        trainingData.setClassIndex(classIndex);
        Instance instanceToClassify = trainingData.remove(trainingData.numInstances() - 1);
        instanceToClassify.setClassMissing();
        trainClassifier(trainingData);
        return instanceToClassify;
    }

    public AbstractClassifier getClassifier() {
        return classifier;
    }

    public AbstractClassifierUtil setClassifier(AbstractClassifier classifier) {
        this.classifier = classifier;
        return this;
    }

    public String getTrainingDataPath() {
        return trainingDataPath;
    }

    public AbstractClassifierUtil setTrainingDataPath(String trainingDataPath) {
        this.trainingDataPath = trainingDataPath;
        return this;
    }

    public AbstractClassifierUtil setTextToClassify(String textToClassify) {
        this.textToClassify = textToClassify;
        return this;
    }

    public String getTextToClassify() {
        return textToClassify;
    }

    public Instances getTrainingData() {
        return trainingData;
    }

    public AbstractClassifierUtil setTrainingData(Instances trainingData) {
        this.trainingData = trainingData;
        return this;
    }

}
