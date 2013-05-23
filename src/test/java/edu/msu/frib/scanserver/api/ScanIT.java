package edu.msu.frib.scanserver.api;

import static edu.msu.frib.scanserver.api.ScanServerClientImpl.*;
import static edu.msu.frib.scanserver.api.Scan.Builder.scan;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import org.junit.AfterClass;
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

    static Commands.Builder commands = commands .....

    /**
     * insert test data
     */
    @BeforeClass
    public static void populateScans() {

        try {
            client = SSCBuilder.serviceURL().create();
            initialChannelCount = client.getAllScans().size();

            Long id = client.queueScan("test",commands);

        } catch (ScanServerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void scanSent() {
        assertTrue("scans were not populated for test", client.getAllScans().size() - initialChannelCount == 1);
    }

    @Test
    public void getScan() {
        client.getScan()
        assertTrue("scan was not found on the server", );
    }

}
