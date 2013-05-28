package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.XmlDevice;
import edu.msu.frib.scanserver.common.commands.XmlLogCommand;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class LogCommand implements Command {
    private final long address;
    private final List<String> devices;

    public static class Builder{
        private long address;
        private List<String> devices = new ArrayList<String>();

        public static Builder logCommand(LogCommand logCommand){
            Builder logCommandBuilder = new Builder();
            logCommandBuilder.devices = logCommand.getDevices();
            logCommandBuilder.address = logCommand.getAddress();

            return logCommandBuilder;
        }

        public static Builder logCommand(List<String> devices){
            Builder logCommandBuilder = new Builder();
            logCommandBuilder.devices = devices;

            return logCommandBuilder;
        }

        public static Builder logCommand(String device){
            Builder logCommandBuilder = new Builder();
            logCommandBuilder.devices.add(device);

            return logCommandBuilder;
        }

        public Builder add(String device){
            this.devices.add(device);
            return this;
        }

        public Builder add(List<String> devices){
            this.devices.addAll(devices);
            return this;
        }

        XmlLogCommand toXml(){
            XmlLogCommand xmlLogCommand = new XmlLogCommand();
            List<XmlDevice> xmlDevices = new ArrayList<XmlDevice>();

            for(String device : devices){
                xmlDevices.add(new XmlDevice(device));
            }
            xmlLogCommand.setDevices(xmlDevices);

            return xmlLogCommand;
        }

        public LogCommand build(){
            return new LogCommand(this);
        }

    }

    LogCommand (XmlLogCommand xmlLogCommand){
        this.address = xmlLogCommand.getAddress();
        List<String> devices = new ArrayList<String>();
        for(XmlDevice xmlDevice:xmlLogCommand.getDevices()){
            devices.add(xmlDevice.getDevice());
        }
        this.devices = Collections.unmodifiableList(devices);
    }

    private LogCommand(Builder builder) {
        this.address = builder.address;
        this.devices = builder.devices;
    }


    public long getAddress() {
        return address;
    }


    public List<String> getDevices() {
        return devices;
    }
}
