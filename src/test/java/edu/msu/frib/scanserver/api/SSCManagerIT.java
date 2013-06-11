package edu.msu.frib.scanserver.api;

import static edu.msu.frib.scanserver.api.commands.LoopCommand.Builder.*;
import static edu.msu.frib.scanserver.api.commands.LogCommand.Builder.*;
import edu.msu.frib.scanserver.api.ScanServerClientImpl.SSCBuilder;

import java.util.ArrayList;
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

        List<String> devices = new ArrayList<String>();
        devices.add("sim://gaussianNoise");
        devices.add("loc://positioner");
        LogCommand log = LogCommand.builder().devices(devices).build();
        LoopCommand loop = LoopCommand.builder().device("loc://positioner").start(1).end(10).step(.5).add(log).build();
        CommandSet commandSet = CommandSet.builder().add(loop).build();
        Long id = ssc.queueScan("test",commandSet);

        Data data = ssc.getScanData(id);
        data.toString();
        List<Command> commands = ssc.getScanCommands(id).getCommands();
        for(Command command : commands){
            command.toString();
        }

        Logger.getLogger(RawLoggingFilter.class.getName()).setLevel(Level.OFF);

    }
}
