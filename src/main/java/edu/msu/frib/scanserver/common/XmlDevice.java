package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "device")
public class XmlDevice {

    @XmlElement
    private String device;

    public XmlDevice(String device){
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
