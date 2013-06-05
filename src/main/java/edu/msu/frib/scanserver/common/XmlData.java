package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElementWrapper;
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
    private List<String> devices;
    private XmlSamples samples;

    public XmlData(){
    }

    @XmlElementWrapper(name="devices")
    @XmlElement(name="device")
    public List<String> getXmlDeviceList() {
        return devices;
    }

    public void setXmlDeviceList(List<String> devices) {
        this.devices = devices;
    }

    @XmlElement
    public XmlSamples getSamples() {
        return samples;
    }

    public void setSamples(XmlSamples samples) {
        this.samples = samples;
    }
}
