package edu.msu.frib.scanserver.common;

import edu.msu.frib.scanserver.api.Scan;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
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
public class XmlScans extends ArrayList<XmlScan> {

    public XmlScans() {
    }


    public XmlScans(XmlScan xmlScan) {
        this.add(xmlScan);
    }

    public XmlScans(List<XmlScan> xmlScans) {
        this.addAll(xmlScans);
    }


    @XmlElementRef(type = XmlScan.class, name = "scan")
    public List<XmlScan> getXmlScanList() {
        return this;
    }

    public void setXmlScanList(List<XmlScan> items) {
        this.clear();
        this.addAll(items);
    }


    public void addXmlScan(XmlScan item) {
        this.add(item);
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
