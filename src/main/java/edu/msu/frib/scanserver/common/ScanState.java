package edu.msu.frib.scanserver.common;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ScanState
{
    /** Scan is waiting to be executed */
    Idle("Idle", false, false),

    /** Scan is currently being executed */
    Running("Running", true, false),

    /** Scan was Running, currently paused */
    Paused("Paused", true, false),

    /** Scan was aborted by user */
    Aborted("Aborted", false, true),

    /** Scan failed because of an error */
    Failed("Failed", false, true),

    /** Scan ended normally, i.e. not aborted or failed */
    Finished("Finished - OK", false, true),

    /** Scan that executed in the past; data has been logged */
    Logged("Logged", false, true);

    final private String name;
    final private boolean active;
    final private boolean done;

    private ScanState(final String name, final boolean active, final boolean done)
    {
        this.name = name;
        this.active = active;
        this.done = done;
    }

    /** @return <code>true</code> if this is an 'active' state,
     *          representing a scan that's currently running (or paused)
     */
    public boolean isActive()
    {
        return active;
    }

    /** @return <code>true</code> if is a 'done' state,
     *          representing a scan that finished one way or another
     */
    public boolean isDone()
    {
        return done;
    }

    /** @return Human-readable representation */
    @Override
    public String toString()
    {
        return name;
    }
}