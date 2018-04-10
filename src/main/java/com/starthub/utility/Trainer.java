package com.starthub.utility;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 * Created by Harrison on 4/4/2018.
 */
public class Trainer{

    private static IBk classifier;
    public Instances instances;

    public Instances getInstances() {
        return instances;
    }

    public void setInstances(Instances instances) {
        this.instances = instances;
    }

    public Trainer(IBk classifier) {
        this.classifier = classifier;
    }

    public IBk trainClassifier(String path) throws Exception {
        instances = DataSetUtil.constructInstances(path);
        int lastIndex = instances.numAttributes() - 1;
        instances.setClassIndex(0);
        classifier.buildClassifier(instances);
        return classifier;
    }

    public IBk trainClassifier(Instances dataSet) throws Exception {
        this.instances = dataSet;
        int lastIndex = this.instances.numAttributes() - 1;
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
