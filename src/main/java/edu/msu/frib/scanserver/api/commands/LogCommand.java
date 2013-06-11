package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlLogCommand;

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
public class LogCommand extends Commands {
    private final long address;
    private final List<String> devices;

    public static abstract class Builder<T extends Builder<T>> extends Commands.Builder<T> {
        private long address;
        private List<String> devices = new ArrayList<String>();


        public T device(String device){
            this.devices.add(device);
            return self();
        }

        public T devices(List<String> devices){
            this.devices.addAll(devices);
            return self();
        }

        public T address(long address){
            this.address = address;
            return self();
        }

        public T fromXml (XmlLogCommand xmlLogCommand){
            this.address = xmlLogCommand.getAddress();
            this.devices = Collections.unmodifiableList(xmlLogCommand.getXmlDeviceList());

            return self();
        }

        public LogCommand build(){
            return new LogCommand(this);
        }

    }
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }


    private LogCommand(Builder<?> builder) {
        super(builder);
        this.address = builder.address;
        this.devices = builder.devices;
    }


    public long getAddress() {
        return address;
    }


    public List<String> getDevices() {
        return devices;
    }

    public XmlLogCommand toXml(){
        XmlLogCommand xmlLogCommand = new XmlLogCommand();
        xmlLogCommand.setAddress(address);
        xmlLogCommand.setXmlDeviceList(devices);
        return xmlLogCommand;
    }
    
    @Override
    public boolean equals(Object o) {
    	if( !(o instanceof LogCommand) ) {
    		return false;
    	}
    	LogCommand other = (LogCommand)o;
    	if( address != other.address ) {
    		return false;
    	}
    	if( !devices.equals(other.devices) ) {
    		return false;
    	}
    	return true;
    }
}
