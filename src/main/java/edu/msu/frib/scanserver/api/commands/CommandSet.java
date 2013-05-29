package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlLoopCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandSet extends CommandComposite {


    public static abstract class Builder<T extends Builder<T>> extends CommandComposite.Builder<T> {

        //public T fromXml(CommandSet commandSet){
        //    return self();
        //}

        //XmlCommandSet toXml(){
        //    return null;
        //}

        public CommandSet build(){
            return new CommandSet(this);
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

    protected CommandSet(Builder<?> builder) {
        super(builder);
    }

}
