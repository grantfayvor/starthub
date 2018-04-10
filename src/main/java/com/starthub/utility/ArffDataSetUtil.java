package com.starthub.utility;

import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Harrison on 4/4/2018.
 */
public class ArffDataSetUtil {

    public static Instances constructInstancesFromArff(String path) throws Exception{
        return new Instances(new BufferedReader(new FileReader(path)));
    }
}
