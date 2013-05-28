package edu.msu.frib.scanserver.api;

import static edu.msu.frib.scanserver.api.ScanServerClientImpl.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import edu.msu.frib.scanserver.api.commands.Command;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/22/13
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScanIT {

    private static int initialChannelCount;
    private static ScanServerClient client;

    static Command commands = null;

    /**
     * insert test data
     */
    @BeforeClass
    public static void init() {

        try {
            client = SSCBuilder.serviceURL().create();
            initialChannelCount = client.getAllScans().size();

        } catch (ScanServerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void queueScan() {
        //Long id = client.queueScan("test",commands);
        //assertTrue("scans were not populated for test", client.getAllScans().size() - initialChannelCount == 1);
    }

    @Test
    public void getScan() {

        //Long id = client.queueScan("test",commands);
        //assertTrue("scan was not found on the server", client.getScan(id).getId() == id);
    }

    @Test
    public void getScanData() {

    }

    @Test
    public void getScanCommands () {

    }

    @Test
    public void deleteScan () {

    }

    @Test
    public void delete () {

    }

    @Test
    public void close() {

    }

}
