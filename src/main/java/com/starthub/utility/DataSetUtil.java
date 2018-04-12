package com.starthub.utility;

import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.ConverterUtils;
import weka.core.converters.DictionarySaver;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Harrison on 4/4/2018.
 */
public class DataSetUtil {

    private static final String DICTIONARY_FILE = "C:/starthub/dataset/" + "Dictionary.txt";
    private static final String TRAINING_DATA = "C:/starthub/dataset/" + "training-data.arff";

    public static Instances constructInstances(String path) throws Exception {
        File directory = new File(path != null ? path : "dataset");
        Instances dataSet = null;
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getCanonicalPath().endsWith(".arff") || file.getCanonicalPath().endsWith(".csv")) {
                    if (dataSet == null) dataSet = new ConverterUtils.DataSource(file.getCanonicalPath()).getDataSet();
                    else dataSet.addAll(new ConverterUtils.DataSource(file.getCanonicalPath()).getDataSet());
                }
            }
        } else {
            System.out.println("the given path is not a directory");
        }
        return dataSet;
    }

    public static Instances constructInstancesFromTrainingData(String dataPath) {
        Instances dataSet = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(dataPath))) {
            dataSet = new Instances(reader);
            return dataSet;
        } catch (IOException ex) {
            System.out.println(ex);
            return dataSet;
        }
    }

    public static Instances constructInstancesFromTokenizedData(String dataPath) {
        Instances dataSet = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(dataPath))) {
            dataSet = new Instances(reader);
            return dataSet;
        } catch (IOException ex) {
            System.out.println(ex);
            return dataSet;
        }
    }

    public Instances tokenize(Instances instances, String stnAttributeRange, String vectorizerAttributeRange, boolean useDictionary) throws Exception {
        StringToNominal filter = new StringToNominal();
        filter.setAttributeRange(stnAttributeRange);
        filter.setInputFormat(instances);
        instances = Filter.useFilter(instances, filter);
        if(useDictionary) return vectorizeFromDictionary(instances, vectorizerAttributeRange);
        else return vectorize(instances, vectorizerAttributeRange);
    }

    public Instances vectorize(Instances instances, String vectorizerAttributeRange) throws Exception {
        File dictionary = new File(DICTIONARY_FILE);
        StringToWordVector vectorizer = new StringToWordVector();
        vectorizer.setAttributeIndices(vectorizerAttributeRange);
        vectorizer.setInputFormat(instances);
        vectorizer.setIDFTransform(true);
        vectorizer.setOutputWordCounts(true);
        vectorizer.setStemmer(new LovinsStemmer());
        vectorizer.setLowerCaseTokens(true);
        vectorizer.setTokenizer(new WordTokenizer());
        vectorizer.setDictionaryFileToSaveTo(dictionary);
        return Filter.useFilter(instances, vectorizer);
    }

    public Instances vectorizeFromDictionary(Instances instances, String vectorizerAttributeRange) throws Exception {
        File dictionary = new File(DICTIONARY_FILE);
        FixedDictionaryStringToWordVector vectorizer = new FixedDictionaryStringToWordVector();
        vectorizer.setDictionaryFile(dictionary);
        vectorizer.setAttributeIndices(vectorizerAttributeRange);
        vectorizer.setInputFormat(instances);
        vectorizer.setIDFTransform(true);
        vectorizer.setOutputWordCounts(true);
        vectorizer.setStemmer(new LovinsStemmer());
        vectorizer.setLowerCaseTokens(true);
        vectorizer.setTokenizer(new WordTokenizer());
        return Filter.useFilter(instances, vectorizer);
    }
}
