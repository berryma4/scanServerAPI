package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCommand;
import edu.msu.frib.scanserver.common.commands.XmlCommandSet;
import edu.msu.frib.scanserver.common.commands.XmlLoopCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandSet extends CommandComposite {

    public static abstract class Builder<T extends Builder<T>> extends CommandComposite.Builder<T> {

        public XmlCommandSet toXml() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            XmlCommandSet xmlCommandSet = new XmlCommandSet();
            List<XmlCommand> xmlCommands = new ArrayList<XmlCommand>();
            for(Command command : this.build().getCommands()){
                Method toXmlMethod = command.getClass().getMethod("toXml");
                xmlCommands.add((XmlCommand)toXmlMethod.invoke(new Object()));
            }
            xmlCommandSet.setCommands(xmlCommands);
            return xmlCommandSet;
        }

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
