package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "scans")
public class XmlScans {

    private List<XmlScan> scans = new ArrayList<XmlScan>();

    public XmlScans() {
    }


    public XmlScans(XmlScan xmlScan) {
        scans.add(xmlScan);
    }

    public XmlScans(List<XmlScan> xmlScans) {
        scans.addAll(xmlScans);
    }

    @XmlElement(name = "scan")
    public List<XmlScan> getXmlScans() {
        return scans;
    }

    public void setXmlScans(List<XmlScan> scans) {
        this.scans = scans;
    }

    public void addXmlScan(XmlScan item) {
        scans.add(item);
    }


    public static String toLogger(List<XmlScan> data) {
        if (data.size() == 0) {
            return "[None]";
        } else {
            StringBuilder s = new StringBuilder();
            s.append("[");
            for (XmlScan c : data) {
                s.append(XmlScan.toLogger(c) + ",");
            }
            s.delete(s.length() - 1, s.length());
            s.append("]");
            return s.toString();
        }
    }


}
