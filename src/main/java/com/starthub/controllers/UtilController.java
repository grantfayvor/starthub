package com.starthub.controllers;

import com.starthub.utility.ClassifierUtil;
import com.starthub.utility.SgmDataSetUtil;
import com.starthub.utility.Trainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.classifiers.lazy.IBk;

/**
 * Created by Harrison on 4/4/2018.
 */

@Controller
@RequestMapping("")
public class UtilController {

    private Trainer trainer;

    public UtilController() {
        this.trainer = new Trainer(new IBk());
    }

    @RequestMapping("/train")
    @ResponseBody
    public void train(@RequestParam("path") String path) throws Exception {
        if (path.endsWith("sgm")) trainer.trainClassifierUsingSgmData(path);
        else trainer.trainClassifier(path);
    }

    @RequestMapping("/testClassifier")
    @ResponseBody
    public String testClassifier(@RequestParam("text") String text, @RequestParam("path") String path) throws Exception {
        return new ClassifierUtil().classify(text, path);
    }

    @RequestMapping("/sgm2arff")
    @ResponseBody
    public void sgm2Arff(@RequestParam("path") String path) throws Exception {
        SgmDataSetUtil.convertToArff(path);
    }

}
