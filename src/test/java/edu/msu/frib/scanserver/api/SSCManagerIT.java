package edu.msu.frib.scanserver.api;

import edu.msu.frib.scanserver.api.ScanServerClientImpl.SSCBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        ssc = SSCBuilder.serviceURL("http://eb-vguest0:4812")
                .create();
        ssc.getAllScans();
        Logger.getLogger(RawLoggingFilter.class.getName()).setLevel(Level.ALL);
    }
}
