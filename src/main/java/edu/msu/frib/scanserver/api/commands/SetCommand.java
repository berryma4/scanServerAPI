package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlSetCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class SetCommand extends Commands{
    private final long address;
    private final double tolerance;
    private final double timeout;
    private final String device;
    private final String value;
    private final String readback;
    private final boolean wait;

    public static abstract class Builder<T extends Builder<T>> extends Commands.Builder<T> {
        private long address;
        private double tolerance;
        private double timeout;
        private String device;
        private String value;
        private String readback;
        private boolean wait;

        public T address(long address){
            this.address = address;
            return self();
        }

        public T tolerance(double tolerance){
            this.tolerance = tolerance;
            return self();
        }

        public T timeout(double timeout){
            this.timeout = timeout;
            return self();
        }

        public T device(String device){
            this.device = device;
            return self();
        }

        public T value(String value){
            this.value = value;
            return self();
        }

        public T readback(String readback){
            this.readback = readback;
            return self();
        }

        public T wait(boolean wait){
            this.wait = wait;
            return self();
        }

        public T fromXml (XmlSetCommand xmlSetCommand){
            this.address = xmlSetCommand.getAddress();
            this.tolerance = xmlSetCommand.getTolerance();
            this.timeout = xmlSetCommand.getTimeout();
            this.device = xmlSetCommand.getDevice();
            this.value = xmlSetCommand.getValue();
            this.readback = xmlSetCommand.getReadback();
            this.wait = xmlSetCommand.isWait();
            return self();
        }

        public SetCommand build(){
            return new SetCommand(this);
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

    private SetCommand(Builder<?> builder) {
        super(builder);
        this.address = builder.address;
        this.tolerance = builder.tolerance;
        this.timeout = builder.timeout;
        this.device = builder.device;
        this.value = builder.value;
        this.readback = builder.readback;
        this.wait = builder.wait;
    }

    public long getAddress() {
        return address;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double getTimeout() {
        return timeout;
    }

    public String getDevice() {
        return device;
    }

    public String getValue() {
        return value;
    }

    public String getReadback() {
        return readback;
    }

    public boolean isWait() {
        return wait;
    }

    public XmlSetCommand toXml(){
        return null;
    }
}
