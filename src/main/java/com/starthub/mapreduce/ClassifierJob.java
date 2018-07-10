package com.starthub.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;
import weka.classifiers.Classifier;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Harrison on 6/7/2018.
 */
public class ClassifierJob extends InputSplit implements Writable, Serializable, Comparable {

    public Classifier classifier;
    public File dataset;
    public long key;
    public int fold;
    public String local;

    public ClassifierJob(Classifier classifier, File dataset, Path local, long key, int fold)
    {
        //utils.LOG.info("d1");
        this.classifier = classifier;
        this.dataset = dataset;
        this.key = key;
        this.fold = fold;
        this.local = local==null?"":local.toString();
    }

    public ClassifierJob()
    {
        this(null,null,null,-1,1);
    }

    public Path getLocal()
    {
        return new Path(local);
    }

    public LongWritable getKey()
    {
        return new LongWritable(key);
    }
    public String toString()
    {
        return "";
    }

    @Override
    public long getLength() throws IOException, InterruptedException {
//        utils.LOG.info("d2");
        return 1;//dataset.length();
    }

    @Override
    public String[] getLocations() throws IOException, InterruptedException {
//        utils.LOG.info("d3");
        return new String[]{};//dataset.toString()};
    }

    @Override
    public void write(DataOutput d) throws IOException {
        //utils.LOG.info("d4");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(this);
        d.write(bos.toByteArray());

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readFields(DataInput di) throws IOException {
//        utils.LOG.info("d5");

        DataInputStream dis = (DataInputStream)di;
        ObjectInput in = new ObjectInputStream(dis);
        try {
            ClassifierJob thejob = (ClassifierJob)in.readObject();
            this.key = thejob.key;
            this.classifier = thejob.classifier;
            this.dataset = thejob.dataset;
            this.fold = thejob.fold;
            this.local = thejob.local;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassifierJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int compareTo(Object t) {
        return this.toString().compareTo(t.toString());
    }
}
