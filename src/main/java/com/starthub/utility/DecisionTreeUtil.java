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
public class DecisionTreeUtil extends AbstractClassifierUtil {

    private final String TRAINING_DATA_PATH = "C:/starthub/dataset/" + "y-combinators-data-set.arff";
    private Map<String, Double> distribution;

    public DecisionTreeUtil() {
        super(new J48());
    }

    public Map<String, Double> test(String textToClassify) throws Exception {
        return super.setTrainingDataPath(TRAINING_DATA_PATH).configureTrainer().distributify(textToClassify);
    }

    public Map<String, Double> getDistribution() {
        return distribution;
    }

    @Override
    public void run() {
        try {
            distribution = test(getTextToClassify());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
