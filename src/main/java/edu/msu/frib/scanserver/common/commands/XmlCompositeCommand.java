package edu.msu.frib.scanserver.common.commands;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class XmlCompositeCommand {
    private List<XmlCompositeCommand> commands = new ArrayList<XmlCompositeCommand>();

    public XmlCompositeCommand() {
    }

    public XmlCompositeCommand(List<XmlCompositeCommand> commands) {
        this.commands = commands;
    }

    public List<XmlCompositeCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<XmlCompositeCommand> commands) {
        this.commands = commands;
    }
}
