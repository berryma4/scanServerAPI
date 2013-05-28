package edu.msu.frib.scanserver.common.commands;

import edu.msu.frib.scanserver.common.XmlDevice;

import javax.xml.bind.annotation.XmlElement;
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
public class XmlLogCommand extends XmlCommand {
    private long address;
    private List<XmlDevice> devices = new ArrayList<XmlDevice>();

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

    @XmlElement
    public List<XmlDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<XmlDevice> devices) {
        this.devices = devices;
    }

}
