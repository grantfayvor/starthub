package com.starthub.mapreduce;

import org.apache.hadoop.io.Text;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Harrison on 6/7/2018.
 */
public class ClassifierFoldResults extends Text {

    ClassifierFoldResults()
    {
        super();
    }
    ClassifierFoldResults(String toString) {
        super(toString);
    }

    ClassifierFoldResults(Evaluation eval, Instances train, Instances test)
    {
        super();
        try {
            String results = "'123','"+System.currentTimeMillis()+"','"+train.numInstances()+"','"+test.numInstances()+"','"+eval.correct()+"','"+eval.incorrect()+"','"+eval.unclassified()+"','"+eval.pctCorrect()+"','"+eval.pctIncorrect()+"','"+eval.pctUnclassified()+"','"+eval.kappa()+"','"+eval.meanAbsoluteError()+"','"+eval.rootMeanSquaredError()+"','"+eval.relativeAbsoluteError()+"','"+eval.rootRelativeSquaredError()+"','"+eval.SFPriorEntropy()+"','"+eval.SFSchemeEntropy()+"','"+eval.SFEntropyGain()+"','"+eval.SFMeanPriorEntropy()+eval.SFMeanSchemeEntropy()+"','"+eval.SFMeanEntropyGain()+"','"+eval.KBInformation()+"','"+eval.KBMeanInformation()+"','"+eval.KBRelativeInformation()+"','";
            results+= eval.truePositiveRate(1)+"','"+eval.numTruePositives(1)+"','"+eval.falsePositiveRate(1)+"','"+eval.numFalsePositives(1)+"','"+eval.trueNegativeRate(1)+"','"+eval.numTrueNegatives(1)+"','"+eval.falseNegativeRate(1)+"','"+eval.numFalseNegatives(1)+"','";
            results+= eval.precision(1)+"','"+eval.recall(1)+"','"+eval.fMeasure(1)+"','"+eval.matthewsCorrelationCoefficient(1)+"','"+eval.areaUnderROC(1)+"','"+eval.areaUnderPRC(1)+"','"+eval.weightedTruePositiveRate()+"','"+eval.weightedFalsePositiveRate()+"','"+eval.weightedTrueNegativeRate()+"','"+eval.weightedFalseNegativeRate()+"','"+eval.weightedPrecision()+"','"+eval.weightedRecall()+"','"+eval.weightedFMeasure()+"','"+eval.weightedMatthewsCorrelation()+"','"+eval.weightedAreaUnderROC()+"','"+eval.weightedAreaUnderPRC()+"','";
            results+= eval.unweightedMacroFmeasure()+"','"+eval.unweightedMicroFmeasure()+"','";
            results+= "1','1','1','1','1','1','1','1','1','1','1'";

            super.set(results);
        } catch (Exception ex) {
            Logger.getLogger(ClassifierFoldResults.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
