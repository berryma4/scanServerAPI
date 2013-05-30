package edu.msu.frib.scanserver.api.commands;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/30/13
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Commands implements Command {

    protected static abstract class Builder<T extends Builder<T>> {
        protected abstract T self();

        public Commands build() {
            return new Commands(this);
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

    protected Commands(Builder<?> builder) {
    }
}
