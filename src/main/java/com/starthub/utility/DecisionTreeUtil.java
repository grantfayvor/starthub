package com.starthub.utility;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harrison on 4/10/2018.
 */
public class DecisionTreeUtil {

    private static J48 classifier;
    private static Trainer trainer = new Trainer(new J48());
    private final String TRAINING_DATA_PATH = "C:/starthub/dataset/" + "y-combinators-data-set.arff";

    public void trainDecisionTree(Instances instances) throws Exception {
        AbstractClassifier abstractClassifier = trainer.trainClassifier(instances);
        if (abstractClassifier instanceof J48) classifier = (J48) abstractClassifier;
        else {
            System.out.println("abstract classifier not an instance of J48");
            classifier = new J48();
        }
    }

    public Map<String, Double> makeDecision(String textToClassify) throws Exception {
        Instances trainingData = DataSetUtil.constructInstancesFromTrainingData(TRAINING_DATA_PATH);
        double[] instanceD = new double[trainingData.numAttributes()];
        instanceD[6] = trainingData.attribute(6).addStringValue(textToClassify);
        trainingData.add(new SparseInstance(1.0, instanceD));
        trainingData = new DataSetUtil().tokenize(trainingData, "last", "1-7", false);
        trainingData.setClassIndex(0);
        Instance instanceToClassify = trainingData.remove(trainingData.numInstances() - 1);
        instanceToClassify.setClassMissing();
        trainDecisionTree(trainingData);
        System.out.println("@@ the training data class to use is " + trainingData.classAttribute().name());
        double[] classDistributions = classifier.distributionForInstance(instanceToClassify);
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < classDistributions.length; i++) {
            result.put(trainingData.classAttribute().value(i), classDistributions[i] * 100);
        }
        return result;
    }
}
