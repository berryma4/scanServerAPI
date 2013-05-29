package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCommand;

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
}
