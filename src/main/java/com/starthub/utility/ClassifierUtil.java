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
public class ClassifierUtil extends AbstractClassifierUtil {

    private final String TRAINING_DATA_PATH = "C:/starthub/dataset/" + "training-data.arff";
    private String classifiedResult;

    public ClassifierUtil() {
        super(new IBk());
    }

    public String test(String textToClassify) throws Exception {
        return super.setTrainingDataPath(TRAINING_DATA_PATH).configureTrainer().classify(textToClassify);
    }

    public String getClassifiedResult() {
        return classifiedResult;
    }

    @Override
    public void run() {
        try {
            classifiedResult = test(getTextToClassify());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
