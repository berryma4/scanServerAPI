package edu.msu.frib.scanserver.common.commands;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "commands")
@XmlSeeAlso({XmlDelayCommand.class,XmlLogCommand.class,XmlLoopCommand.class})
public class XmlCommandSet {

    private List<XmlCommand> commands = new ArrayList<XmlCommand>();

    public XmlCommandSet() {
    }

    @XmlAnyElement(lax=true)
    public List<XmlCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<XmlCommand> commands) {
        this.commands = commands;
    }
}
