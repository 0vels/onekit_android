package cn.onekit;

import org.w3c.dom.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UI {
    /**
     * 加载布局
     *
     * @param path 路径
     * @return XML
     */
    public static Document load(String path) {
        File file = new File(path);
        String str = "";
        try {
            BufferedReader rd = new BufferedReader(new FileReader(file));
            String s = rd.readLine();
            while (null != s) {
                str += s + "<br/>";
                s = rd.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XML.parse(str);

    }
/*
        DocumentBuilder documentBuilder = null;
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource inputSource = null;
        try {
            inputSource = new InputSource(new FileInputStream(file));
            documentBuilder = factory.newDocumentBuilder();
            doc = documentBuilder.parse(inputSource);
            return doc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
