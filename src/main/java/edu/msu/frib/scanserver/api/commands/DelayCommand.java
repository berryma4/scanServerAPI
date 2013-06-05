package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlDelayCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DelayCommand extends Commands {
    private final long address;
    private final double seconds;

    public static abstract class Builder<T extends Builder<T>> extends Commands.Builder<T> {
        private long address;
        private double seconds;

        public T address(long address){
            this.address = address;
            return self();
        }

        public T seconds(double seconds){
            this.seconds = seconds;
            return self();
        }

        public T fromXml (XmlDelayCommand xmlDelayCommand){
            xmlDelayCommand.getAddress();
            xmlDelayCommand.getSeconds();
            return self();
        }

        public DelayCommand build(){
            return new DelayCommand(this);
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

    private DelayCommand(Builder<?> builder) {
        super(builder);
        this.address = builder.address;
        this.seconds = builder.seconds;
    }

    public long getAddress() {
        return address;
    }

    public double getSeconds() {
        return seconds;
    }

    public XmlDelayCommand toXml(){
        return null;
    }
}
