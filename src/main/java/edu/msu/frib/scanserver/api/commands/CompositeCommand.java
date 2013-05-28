package edu.msu.frib.scanserver.api.commands;

import edu.msu.frib.scanserver.common.commands.XmlCompositeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/28/13
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CompositeCommand implements Command {

    private List<Command> command = new ArrayList<Command>();

    public void addCommand(Command commands){
        command.add(commands);
    }

}
