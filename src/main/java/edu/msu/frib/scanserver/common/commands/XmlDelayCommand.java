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
public class XmlDelayCommand extends XmlCommand {
    private double seconds;

    private XmlDelayCommand(){
    }

    public XmlDelayCommand(double seconds) {
        this.seconds = seconds;
    }

    @XmlElement
    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}
