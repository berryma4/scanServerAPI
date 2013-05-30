package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCommand;
import edu.msu.frib.scanserver.common.commands.XmlCommandSet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandComposite implements Command {

    private final List<Command> commands;

    protected static abstract class Builder<T extends Builder<T>> {
        private List<Command> commands = new ArrayList<Command>();

        protected abstract T self();

        public T add (Command command) {
            this.commands.add(command);
            return self();
        }

        public T add (List<Command> commands) {
            this.commands.addAll(commands);
            return self();
        }

        public T fromXml(List<XmlCommand> xmlCommands) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            for(XmlCommand xmlCommand:xmlCommands){
                String classString = xmlCommand.getClass().getName().replace("common","api").replace("Xml","");
                Class commandClass = Class.forName(classString);
                Method builderMethod = commandClass.getMethod("builder");
                Object object = builderMethod.invoke(new Object());
                Method fromXmlMethod = object.getClass().getMethod("fromXml",xmlCommand.getClass());
                Method buildMethod = object.getClass().getMethod("build");
                Object builder = fromXmlMethod.invoke(object, xmlCommand);
                commands.add((Command)buildMethod.invoke(builder));
            }
            return self();
        }

        public CommandComposite build() {
            return new CommandComposite(this);
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

    protected CommandComposite(Builder<?> builder) {
        this.commands = builder.commands;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
