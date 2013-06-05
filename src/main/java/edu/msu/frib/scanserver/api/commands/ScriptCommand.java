package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlScriptCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScriptCommand extends Commands {
    private final long address;
    private final String path;

    public static abstract class Builder<T extends Builder<T>> extends Commands.Builder<T> {
        private long address;
        private String path;

        public T address(long address){
            this.address = address;
            return self();
        }

        public T path(String path){
            this.path = path;
            return self();
        }

        public T fromXml (XmlScriptCommand xmlScriptCommand){
            this.address = xmlScriptCommand.getAddress();
            this.path = xmlScriptCommand.getPath();
            return self();
        }

        public ScriptCommand build(){
            return new ScriptCommand(this);
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

    private ScriptCommand(Builder<?> builder) {
        super(builder);
        this.address = builder.address;
        this.path = builder.path;
    }

    public long getAddress() {
        return address;
    }

    public String getPath() {
        return path;
    }

    public XmlScriptCommand toXml(){
        return null;
    }
}
