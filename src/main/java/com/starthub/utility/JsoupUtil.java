package com.starthub.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Harrison on 4/4/2018.
 */
public class JsoupUtil {

    private Document document;

    public static Document parseFile(String pathName, String charsetName) throws IOException{
        return Jsoup.parse(new File(pathName), charsetName);
    }

    public static Document parseHtml(String html) {
        return Jsoup.parse(html);
    }

    public static Document parseXml(String xml) {
        return Jsoup.parse(xml, "", Parser.xmlParser());
    }
}
