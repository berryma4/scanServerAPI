package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCompositeCommand;
import edu.msu.frib.scanserver.common.commands.XmlLoopCommand;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoopCommand extends CompositeCommand {
    private final long address;
    private final String device;
    private final double start;
    private final double end;
    private final double step;
    private final String readback;
    private final boolean wait;
    private final double tolerance;
    private final double timeout;

    public static class Builder {
        private long address;
        private String device;
        private double start;
        private double end;
        private double step;
        private String readback = "";
        private boolean wait = true;
        private double tolerance = 0.1;
        private double timeout = 0.0;

        public static Builder loopCommand(LoopCommand loopCommand) {
            Builder loopCommandBuilder = new Builder();
            loopCommandBuilder.address = loopCommand.getAddress();
            loopCommandBuilder.device = loopCommand.getDevice();
            loopCommandBuilder.start = loopCommand.getStart();
            loopCommandBuilder.end = loopCommand.getEnd();
            loopCommandBuilder.step = loopCommand.getStep();
            loopCommandBuilder.readback = loopCommand.getReadback();
            loopCommandBuilder.wait = loopCommand.isWait();
            loopCommandBuilder.tolerance = loopCommand.getTolerance();
            loopCommandBuilder.timeout = loopCommand.getTimeout();

            return loopCommandBuilder;
        }

        public static Builder loopCommand(String device){
            Builder loopCommandBuilder = new Builder();
            loopCommandBuilder.device = device;

            return loopCommandBuilder;
        }

        public Builder address(long address){
            this.address = address;
            return this;
        }

        public Builder start(double start){
            this.start = start;
            return this;
        }

        public Builder end(double end){
            this.end = end;
            return this;
        }

        public Builder step(double step){
            this.step = step;
            return this;
        }

        public Builder readback(String readback){
            this.readback = readback;
            return this;
        }

        public Builder wait(boolean wait){
            this.wait = wait;
            return this;
        }

        public Builder tolerance(double tolerance){
            this.tolerance = tolerance;
            return this;
        }

        public Builder timeout(double timeout){
            this.timeout = timeout;
            return this;
        }

        XmlLoopCommand toXml(){
            return null;
        }

        public LoopCommand build(){
            return new LoopCommand(this);
        }
    }

    LoopCommand (XmlLoopCommand xmlLoopCommand){
        this.address = xmlLoopCommand.getAddress();
        this.device = xmlLoopCommand.getDevice();
        this.start = xmlLoopCommand.getStart();
        this.end = xmlLoopCommand.getEnd();
        this.step = xmlLoopCommand.getStep();
        this.readback = xmlLoopCommand.getReadback();
        this.wait = xmlLoopCommand.isWait();
        this.tolerance = xmlLoopCommand.getTolerance();
        this.timeout = xmlLoopCommand.getTimeout();
    }

    private LoopCommand (Builder builder){
        this.address = builder.address;
        this.device = builder.device;
        this.start = builder.start;
        this.end = builder.end;
        this.step = builder.step;
        this.readback = builder.readback;
        this.wait = builder.wait;
        this.tolerance = builder.tolerance;
        this.timeout = builder.timeout;
    }

    public long getAddress() {
        return address;
    }

    public String getDevice() {
        return device;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public double getStep() {
        return step;
    }

    public String getReadback() {
        return readback;
    }

    public boolean isWait() {
        return wait;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double getTimeout() {
        return timeout;
    }
}
