package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.Comparison;
import edu.msu.frib.scanserver.common.commands.XmlWaitCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class WaitCommand extends Commands {
    private final long address;
    private final Comparison comparison;
    private final String device;
    private final double timeout;
    private final double tolerance;
    private final double value;

    public static abstract class Builder<T extends Builder<T>> extends Commands.Builder<T> {
        private long address;
        private Comparison comparison;
        private String device;
        private double timeout;
        private double tolerance;
        private double value;

        public T address(long address){
            this.address = address;
            return self();
        }

        public T comparison(Comparison comparison){
            this.comparison = comparison;
            return self();
        }

        public T device(String device){
            this.device = device;
            return self();
        }

        public T timeout(double timeout){
            this.timeout = timeout;
            return self();
        }

        public T tolerance(double tolerance){
            this.tolerance = tolerance;
            return self();
        }

        public T value(double value){
            this.value = value;
            return self();
        }

        public T fromXml (XmlWaitCommand xmlWaitCommand){
            this.address = xmlWaitCommand.getAddress();
            this.comparison = xmlWaitCommand.getComparison();
            this.device = xmlWaitCommand.getDevice();
            this.timeout = xmlWaitCommand.getTimeout();
            this.tolerance = xmlWaitCommand.getTolerance();
            this.value = xmlWaitCommand.getValue();

            return self();
        }

        public WaitCommand build(){
            return new WaitCommand(this);
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

    private WaitCommand(Builder<?> builder) {
        super(builder);
        this.address = builder.address;
        this.comparison = builder.comparison;
        this.device = builder.device;
        this.timeout = builder.timeout;
        this.tolerance = builder.tolerance;
        this.value = builder.value;
    }

    public long getAddress() {
        return address;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public String getDevice() {
        return device;
    }

    public double getTimeout() {
        return timeout;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double getValue() {
        return value;
    }

    public XmlWaitCommand toXml(){
        XmlWaitCommand xmlWaitCommand = new XmlWaitCommand();
        xmlWaitCommand.setAddress(this.address);
        xmlWaitCommand.setComparison(this.comparison);
        xmlWaitCommand.setDevice(this.device);
        xmlWaitCommand.setTimeout(this.timeout);
        xmlWaitCommand.setTolerance(this.tolerance);
        xmlWaitCommand.setValue(this.value);
        return xmlWaitCommand;
    }
    
    @Override
    public boolean equals(Object o) {
	    if( !(o instanceof WaitCommand) ) {
	    	return false;
	    }
	    WaitCommand other = (WaitCommand)o;
	    if( address != other.address ) {
	    	return false;
	    }
	    if( !comparison.equals(other.comparison) ) {
	    	return false;
	    }
	    if( !device.equals(other.device) ) {
	    	return false;
	    }
	    if( timeout != other.timeout ) {
	    	return false;
	    }
	    if( tolerance != other.tolerance ) {
	    	return false;
	    }
	    if( value != other.value ) {
	    	return false;
	    }
	    return true;
    }
}
