package edu.msu.frib.scanserver.api;

import static edu.msu.frib.scanserver.api.ScanServerClientImpl.SSCBuilder.serviceURL;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/23/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScanServer {

    public static final String DEFAULT_CLIENT = "composite_client";
    private static volatile ScanServerClient client;

    private ScanServer() {

    }

    public static void setClient(ScanServerClient client) {
        ScanServer.client = client;
    }

    /**
     *
     * @return returns the default {@link ScanServerClient}.
     */
    public static ScanServerClient getClient() {
        if(client == null){
            ScanServer.client = serviceURL().create();
        }
        return client;
    }


}
