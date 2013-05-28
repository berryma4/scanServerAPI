package edu.msu.frib.scanserver.common;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<Long, Date> {

    @Override
    public Long marshal(Date v) throws Exception {
        return v.getTime()/1000;
    }

    @Override
    public Date unmarshal(Long timeStamp) throws Exception {
        return new Date(timeStamp*1000);
    }

}
