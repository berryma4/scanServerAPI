package edu.msu.frib.scanserver.common;


import org.epics.util.time.TimeDuration;
import org.epics.util.time.Timestamp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */

public class TimeDurationLongAdapter extends XmlAdapter<Long, TimeDuration> {

    @Override
    public Long marshal(TimeDuration v) throws Exception {
        return v.getSec();
    }

    @Override
    public TimeDuration unmarshal(Long LongTimestamp) throws Exception {
        return TimeDuration.ofNanos(LongTimestamp);
    }

}
