package edu.msu.frib.scanserver.api;

import edu.msu.frib.scanserver.api.commands.CommandComposite;
import edu.msu.frib.scanserver.api.commands.CommandSet;

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

    public Scan getScan(Long id);

    public Data getScanData(Long id);

    public CommandComposite getScanCommands(Long id);

    public void deleteScan(Long id);

    public void delete(Long id);

    public Long queueScan(String name, CommandSet commandSet);

    public void close();
}
