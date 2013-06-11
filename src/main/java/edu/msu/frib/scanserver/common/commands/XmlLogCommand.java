package edu.msu.frib.scanserver.common.commands;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "log")
public class XmlLogCommand implements XmlCommand {
    private long address;
    private List<String> devices = new ArrayList<String>();

    public XmlLogCommand() {
    }

    public XmlLogCommand(long address) {
        this.address = address;
    }

    @XmlElement
    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    @XmlElementWrapper(name="devices")
    @XmlElement(name="device")
    public List<String> getXmlDeviceList() {
        return devices;
    }

    public void setXmlDeviceList(List<String> devices) {
        this.devices = devices;
    }

}
