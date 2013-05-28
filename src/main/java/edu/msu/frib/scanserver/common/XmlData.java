package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */

@XmlRootElement(name = "data")
public class XmlData {

    @XmlElement(name = "devices")
    private List<String> devices;

    @XmlElement
    private XmlSamples samples;


    public XmlData(){
    }

    public List<String> getXmlDeviceList() {
        return devices;
    }

    public void setXmlDeviceList(List<String> devices) {
        this.devices = devices;
    }

    public XmlSamples getSamples() {
        return samples;
    }

    public void setSamples(XmlSamples samples) {
        this.samples = samples;
    }
}
