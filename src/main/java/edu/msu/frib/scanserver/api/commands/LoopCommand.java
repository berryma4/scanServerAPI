package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCommand;
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
public class LoopCommand extends CommandComposite {
    private final long address;
    private final String device;
    private final double start;
    private final double end;
    private final double step;
    private final String readback;
    private final boolean wait;
    private final double tolerance;
    private final double timeout;

    public static abstract class Builder<T extends Builder<T>> extends CommandComposite.Builder<T> {
        private long address;
        private String device;
        private double start;
        private double end;
        private double step;
        private String readback = "";
        private boolean wait = true;
        private double tolerance = 0.1;
        private double timeout = 0.0;

        public T device(String device){
            this.device = device;
            return self();
        }

        public T address(long address){
            this.address = address;
            return self();
        }

        public T start(double start){
            this.start = start;
            return self();
        }

        public T end(double end){
            this.end = end;
            return self();
        }

        public T step(double step){
            this.step = step;
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

        public T tolerance(double tolerance){
            this.tolerance = tolerance;
            return self();
        }

        public T timeout(double timeout){
            this.timeout = timeout;
            return self();
        }

        public T fromXml(XmlLoopCommand xmlLoopCommand) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            this.address = xmlLoopCommand.getAddress();
            this.device = xmlLoopCommand.getDevice();
            this.start = xmlLoopCommand.getStart();
            this.end = xmlLoopCommand.getEnd();
            this.step = xmlLoopCommand.getStep();
            this.readback = xmlLoopCommand.getReadback();
            this.wait = xmlLoopCommand.isWait();
            this.tolerance = xmlLoopCommand.getTolerance();
            this.timeout = xmlLoopCommand.getTimeout();
            this.add(CommandSet.builder().fromXml(xmlLoopCommand.getBody()).build().getCommands());
            return self();
        }

        public LoopCommand build(){
            return new LoopCommand(this);
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

    protected  LoopCommand(Builder<?> builder) {
        super(builder);
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

    public XmlLoopCommand toXml() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        XmlLoopCommand xmlLoopCommand = new XmlLoopCommand();
        xmlLoopCommand.setAddress(this.address);
        xmlLoopCommand.setDevice(this.device);
        xmlLoopCommand.setEnd(this.end);
        xmlLoopCommand.setReadback(this.readback);
        xmlLoopCommand.setStart(this.start);
        xmlLoopCommand.setStep(this.step);
        xmlLoopCommand.setTimeout(this.timeout);
        xmlLoopCommand.setTolerance(this.tolerance);
        xmlLoopCommand.setWait(this.wait);
        List<XmlCommand> xmlCommands = new ArrayList<XmlCommand>();
        for(Command command : this.getCommands()){
            Method toXmlMethod = command.getClass().getMethod("toXml");
            xmlCommands.add((XmlCommand)toXmlMethod.invoke(command));
        }
        xmlLoopCommand.setBody(xmlCommands);
        return xmlLoopCommand;
    }
}
