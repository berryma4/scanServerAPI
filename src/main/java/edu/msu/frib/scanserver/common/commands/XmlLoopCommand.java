package edu.msu.frib.scanserver.common.commands;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "loop")
public class XmlLoopCommand implements XmlCommand {

    private List<XmlCommand> body = new ArrayList<XmlCommand>();

    private long address;
    private String device;
    private double start;
    private double end;
    private double step;
    private String readback;
    private boolean wait;
    private double tolerance;
    private double timeout;


    public XmlLoopCommand() {
    }

    public XmlLoopCommand(String device) {
        this.device = device;
    }

    @XmlElementWrapper
    @XmlAnyElement(lax=true)
    public List<XmlCommand> getBody() {
        return body;
    }

    public void setBody(List<XmlCommand> body) {
        this.body = body;
    }

    @XmlElement
    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    @XmlElement
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @XmlElement
    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    @XmlElement
    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @XmlElement
    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @XmlElement
    public String getReadback() {
        return readback;
    }

    public void setReadback(String readback) {
        this.readback = readback;
    }

    @XmlElement
    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    @XmlElement
    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    @XmlElement
    public double getTimeout() {
        return timeout;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }


}
