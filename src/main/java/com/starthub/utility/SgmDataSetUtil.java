package com.starthub.utility;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.ArffSaver;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harrison on 4/4/2018.
 */
public class SgmDataSetUtil {

    public static void convertToArff(String pathName) throws Exception {
        new Thread(){
            @Override
            public void run() {
                File directory = new File(pathName != null ? pathName : "/dataset");
                if (directory.isDirectory()) {
                    File[] files = directory.listFiles();
                    for (File file : files) {
                        try {
                            if (file.getCanonicalPath().contains(".sgm")) {
                                Instances instances = constructInstancesFromSgm(file.getCanonicalPath());
                                ArffSaver saver = new ArffSaver();
                                saver.setInstances(instances);
                                saver.setFile(new File(file.getCanonicalPath().replace("sgm", "arff")));
                                saver.writeBatch();
                            } else {
                                System.out.println("not an sgm file");
                            }
                        } catch (Exception ex) {
                            System.out.print(ex);
                        }
                    }
                } else {
                    System.out.println("the given path is not a directory");
                }
            }
        }.run();

    }

    public static Instances constructInstancesFromSgm(String pathName) throws Exception {
        String xml = readFile(pathName);
        Document document = JsoupUtil.parseXml(xml);
        Elements topics = document.select("TOPICS");
        Elements texts = document.select("TEXT");
        ArrayList<Attribute> attributes = new ArrayList<>();
        List<Instance> instances = new ArrayList<>();
        List<String> attributeValues = null;
        Attribute value = new Attribute("value", attributeValues);
        Attribute tag = new Attribute("class", attributeValues);
        attributes.add(value);
        attributes.add(tag);
        int dataSize = texts.size() < topics.size() ? texts.size() : topics.size();
        instances.add(new SparseInstance(attributes.size()));
        Instances dataSet = new Instances("DataSet", attributes, dataSize);
        for (int dataIndex = 0; dataIndex < dataSize; dataIndex++) {
            Element body = texts.get(dataIndex).select("BODY").first();
            body = body != null && body.text() != null ? body : texts.get(dataIndex).select("TITLE").first();
            for (Element topic : topics.get(dataIndex).select("D")) {
                for (int instanceIndex = 0; instanceIndex < instances.size(); instanceIndex++) {
                    if (topic != null && topic.text() != null && body != null && body.text() != null) {
                        Instance instance = instances.get(instanceIndex);
                        instance.setValue(value, body.text());
                        instance.setValue(tag, topic.text().toUpperCase());
                        dataSet.add(instance);
                    }
                }
            }
        }
        return dataSet;
//        return new DataSetUtil().tokenize(dataSet, "last", "first", false);
    }

    private static String readFile(String pathName) {
        try (FileReader fileReader = new FileReader(pathName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException ex) {
            System.out.println(ex);
            return "";
        }
    }
}
