package edu.msu.frib.scanserver.common;


import org.epics.util.time.Timestamp;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimestampLongAdapter extends XmlAdapter<Long, Timestamp> {

    @Override
    public Long marshal(Timestamp v) throws Exception {
        return v.getSec();
    }

    @Override
    public Timestamp unmarshal(Long timeStamp) throws Exception {
        return Timestamp.of(timeStamp,0);
    }

}
