package com.nq.spring.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * Document工具类，提供document相关操作
 * @author niuqian
 */
public class DocumentReader {

    public static Document createDocument(InputStream inputStream){
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(inputStream);
        }catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
}
