package edu.msu.frib.scanserver.common;

import edu.msu.frib.scanserver.common.commands.XmlCompositeCommand;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "scan")
public class XmlScan {

    private Long id;
    private String name;
    private Date created;
    private ScanState state;
    private Date runtime;
    private Long percentage;
    private Date finish;
    private String command;
    private String error;

    public XmlScan(){
    }

    public XmlScan(Long id, String name, Date created, ScanState state, Date runtime, Long percentage, Date finish, String command, String error) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.state = state;
        this.runtime = runtime;
        this.percentage = percentage;
        this.finish = finish;
        this.command = command;
        this.error = error;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement
    public ScanState getState() {
        return state;
    }

    public void setState(ScanState state) {
        this.state = state;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getRuntime() {
        return runtime;
    }

    public void setRuntime(Date runtime) {
        this.runtime = runtime;
    }

    @XmlElement
    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    @XmlElement
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @XmlElement
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static String toLogger(XmlScan data) {
                 return data.getId() + "(" + data.getName() + ")";
             }
}


