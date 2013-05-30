package edu.msu.frib.scanserver.api;

import static edu.msu.frib.scanserver.api.commands.LoopCommand.Builder.*;
import static edu.msu.frib.scanserver.api.commands.LogCommand.Builder.*;
import edu.msu.frib.scanserver.api.ScanServerClientImpl.SSCBuilder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.msu.frib.scanserver.api.commands.Command;
import edu.msu.frib.scanserver.api.commands.CommandSet;
import edu.msu.frib.scanserver.api.commands.LogCommand;
import edu.msu.frib.scanserver.api.commands.LoopCommand;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/22/13
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SSCManagerIT {

    private static Logger logger = Logger.getLogger(SSCManagerIT.class.getName());

    @BeforeClass
    public static void beforeTests() {
    }

    @AfterClass
    public static void afterTests() {
    }

    @Test
    public void simpleReadConnection() {
        ScanServerClient ssc;
        ssc = SSCBuilder.serviceURL("http://localhost:4812")
                .create();
        Logger.getLogger(RawLoggingFilter.class.getName()).setLevel(Level.ALL);
        List<Scan> scans = ssc.getAllScans();
        Scan scan = ssc.getScan(Long.valueOf(42));
        scan.toString();
        List<Command> commands = ssc.getScanCommands(Long.valueOf(42)).getCommands();
        for(Command command : commands){
            command.toString();
        }

        LogCommand log = LogCommand.builder().device("test").build();
        LoopCommand loop2 = LoopCommand.builder().device("test").start(234).end(234).step(.01).add(log).build();
        LoopCommand loop = LoopCommand.builder().device("test").start(234).end(234).step(.01).add(log).add(loop2).build();
        CommandSet commandSet = CommandSet.builder().add(loop).build();

        Logger.getLogger(RawLoggingFilter.class.getName()).setLevel(Level.OFF);

    }
}
