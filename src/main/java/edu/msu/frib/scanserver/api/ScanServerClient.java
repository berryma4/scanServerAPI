package edu.msu.frib.scanserver.api;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ScanServerClient {

    public List<Scan> getAllScans();

    public Scan getScan(long id);

    public Data getScanData(long id);

    public Commands getScanCommands(long id);

    public void deleteScan(long id);

    public void delete(long id);

    public void queueScan(String name, Commands commands);

    public void close();
}
