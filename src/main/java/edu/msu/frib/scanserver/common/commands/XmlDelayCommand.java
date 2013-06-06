package edu.msu.frib.scanserver.common.commands;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "delay")
public class XmlDelayCommand implements XmlCommand {
    private long address;
    private double seconds;

    public XmlDelayCommand() {
    }

    @XmlElement
    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    @XmlElement
    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }
}
