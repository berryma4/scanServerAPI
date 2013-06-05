package edu.msu.frib.scanserver.api;

import edu.msu.frib.scanserver.common.ScanState;
import edu.msu.frib.scanserver.common.XmlScan;
import org.epics.util.time.TimeDuration;
import org.epics.util.time.Timestamp;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scan {

    private final Long id;
    private final String name;
    private final Timestamp created;
    private final ScanState state;
    private final TimeDuration runtime;
    private final Long percentage;
    private final Timestamp finish;
    private final String command;
    private final String error;

    public static class Builder {
        private Long id;
        private String name;
        private Timestamp created;
        private ScanState state;
        private TimeDuration runtime;
        private Long percentage;
        private Timestamp finish;
        private String command;
        private String error;

        public static Builder scan(Scan scan) {
            Builder scanBuilder = new Builder();
            scanBuilder.id = scan.getId();
            scanBuilder.name = scan.getName();
            scanBuilder.created = scan.getCreated();
            scanBuilder.state = scan.getState();
            scanBuilder.runtime = scan.getRuntime();
            scanBuilder.percentage = scan.getPercentage();
            scanBuilder.finish = scan.getFinish();
            scanBuilder.command = scan.getCommand();
            scanBuilder.error = scan.getError();

            return scanBuilder;
        }

        public static Builder scan(Long id, String name, Timestamp created, ScanState state, TimeDuration runtime, Long percentage, Timestamp finish, String command, String error) {
            Builder scanBuilder = new Builder();
            scanBuilder.id = id;
            scanBuilder.name = name;
            scanBuilder.created = created;
            scanBuilder.state = state;
            scanBuilder.runtime = runtime;
            scanBuilder.percentage = percentage;
            scanBuilder.finish = finish;
            scanBuilder.command = command;
            scanBuilder.error = error;

            return scanBuilder;
        }

        XmlScan toXml() {
            return new XmlScan(id, name, created, state, runtime, percentage, finish, command, error);
        }

        public Scan build() {
            return new Scan(this);
        }
    }

    Scan(XmlScan xmlScan){
        this.id = xmlScan.getId();
        this.name = xmlScan.getName();
        this.created = xmlScan.getCreated();
        this.state = xmlScan.getState();
        this.runtime = xmlScan.getRuntime();
        this.percentage = xmlScan.getPercentage();
        this.finish = xmlScan.getFinish();
        this.command = xmlScan.getCommand();
        this.error = xmlScan.getError();
    }

    private Scan (Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.created = builder.created;
        this.state = builder.state;
        this.runtime = builder.runtime;
        this.percentage = builder.percentage;
        this.finish = builder.finish;
        this.command = builder.command;
        this.error = builder.error;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreated() {
        return created;
    }

    public ScanState getState() {
        return state;
    }

    public TimeDuration getRuntime() {
        return runtime;
    }

    public Long getPercentage() {
        return percentage;
    }

    public Timestamp getFinish() {
        return finish;
    }

    public String getCommand() {
        return command;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scan scan = (Scan) o;

        if (!id.equals(scan.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Scan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", state=" + state +
                ", runtime=" + runtime +
                ", percentage=" + percentage +
                ", finish=" + finish +
                ", command='" + command + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
