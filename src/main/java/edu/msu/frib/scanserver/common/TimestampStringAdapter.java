package edu.msu.frib.scanserver.common;

import org.epics.util.time.Timestamp;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */

public class TimestampStringAdapter extends XmlAdapter<String, Timestamp> {

    @Override
    public String marshal(Timestamp v) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd H:m:s.S", Locale.ENGLISH).format(v.toDate());
    }

    @Override
    public Timestamp unmarshal(String dateString) throws Exception {
        //2013-05-30 10:35:12.873
        return Timestamp.of(new SimpleDateFormat("yyyy-MM-dd H:m:s.S", Locale.ENGLISH).parse(dateString));
    }

}
