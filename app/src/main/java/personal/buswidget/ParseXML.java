package personal.buswidget;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2016-01-04.
 */
public class ParseXML {
    public String parseXML(InputStream inputStream) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document XMLDoc = documentBuilder.parse(inputStream);
        Element rootElement = XMLDoc.getDocumentElement();
        NodeList routeNo = rootElement.getElementsByTagName("RouteNo");
        NodeList routeHeading = rootElement.getElementsByTagName("RouteHeading");
        Node currentElement = null;
        String allRoutes = "";
        for (int i = 0; i < routeNo.getLength(); i++) {
            currentElement = routeNo.item(i);
            allRoutes += currentElement.getTextContent();
            currentElement = routeHeading.item(i);
            allRoutes += "\t" + currentElement.getTextContent() + "\n";
        }
        return allRoutes;
    }
}
