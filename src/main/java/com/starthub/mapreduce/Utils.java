package com.starthub.mapreduce;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;
import weka.classifiers.Classifier;
import weka.core.OptionHandler;

/**
 * Created by Harrison on 6/7/2018.
 */
public class Utils {

    public static String classifierToString(Classifier c)
    {
        String str = c.getClass().getName();
        if (c instanceof OptionHandler)
            str += " " + weka.core.Utils.joinOptions(((OptionHandler) c).getOptions());
        return str;
    }
    public static final Log LOG = LogFactory.getLog(Utils.class);

    static String extraZeros(long key) {
        return String.format("%06d", key);
    }
}
