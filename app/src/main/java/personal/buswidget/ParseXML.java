package personal.buswidget;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
        String stopNo = rootElement.getElementsByTagName("StopNo").item(0).getTextContent();
        String stopDescription = rootElement.getElementsByTagName("StopLabel").item(0).getTextContent();
        String routeNo = rootElement.getElementsByTagName("RouteNo").item(0).getTextContent();
        String routeHeading = rootElement.getElementsByTagName("TripDestination").item(0).getTextContent();
        String startTime = rootElement.getElementsByTagName("TripStartTime").item(0).getTextContent();
        String[] HHMM = startTime.split(":");
        int hh = Integer.valueOf(HHMM[0]);
        int mm = Integer.valueOf(HHMM[1]);
        boolean hhSingle;
        boolean mmSingle;
        String ETA = "";
        int adjustTime = Integer.valueOf(rootElement.getElementsByTagName("AdjustedScheduleTime").item(0).getTextContent());
        if (adjustTime <= 60) {
            if (mm + adjustTime > 60) {
                int x = mm + adjustTime;
                mm = x % 60;
                hh += (x / 60);
            } else {
                mm += adjustTime;
            }
        } else {
            hh += (adjustTime / 60);
            mm += (adjustTime % 60);
        }
        if (hh >= 24) {
            hh = hh % 24;
        }
        ETA = hh + ":" + mm;
        int Speed = 0;
        if (!rootElement.getElementsByTagName("GPSSpeed").item(0).getTextContent().isEmpty()) {
            Speed = (int) Math.ceil(Float.valueOf(rootElement.getElementsByTagName("GPSSpeed").item(0).getTextContent()));
        }

        String allAboutRoutes = stopNo + "@" + stopDescription + "@" + routeNo + "@" + routeHeading
                + "@" + Speed + "@" + ETA;

//        String getStopInfo = stopNo.item(0).getTextContent() + ":" + stopDescription.item(0).getTextContent();
//        allAboutRoutes += getStopInfo;
//        Node currentElement = null;
//        for (int i = 0; i < routeNo.getLength(); i++) {
//            currentElement = routeNo.item(i);
//            allAboutRoutes += ":" + currentElement.getTextContent();
//            currentElement = routeHeading.item(i);
//            allAboutRoutes += ":" + currentElement.getTextContent();
//        }
        return allAboutRoutes;
    }
}
