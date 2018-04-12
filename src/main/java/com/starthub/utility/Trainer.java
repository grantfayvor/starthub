package com.starthub.utility;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Created by Harrison on 4/4/2018.
 */
public class Trainer{

    private static AbstractClassifier classifier;
    public Instances instances;

    public Trainer(AbstractClassifier classifier) {
        this.classifier = classifier;
    }

    public Instances getInstances() {
        return instances;
    }

    public void setInstances(Instances instances) {
        this.instances = instances;
    }

    public AbstractClassifier trainClassifier(String path) throws Exception {
        instances = DataSetUtil.constructInstances(path);
        instances.setClassIndex(0);
        classifier.buildClassifier(instances);
        return classifier;
    }

    public AbstractClassifier trainClassifier(Instances dataSet) throws Exception {
        this.instances = dataSet;
        this.instances.setClassIndex(0);
        classifier.buildClassifier(this.instances);
        return classifier;
    }

    public void trainClassifierUsingSgmData(String path) throws Exception {
        instances = SgmDataSetUtil.constructInstancesFromSgm(path);
        int lastIndex = instances.numAttributes() - 1;
        instances.setClassIndex(lastIndex);
        classifier.buildClassifier(instances);
    }

}
