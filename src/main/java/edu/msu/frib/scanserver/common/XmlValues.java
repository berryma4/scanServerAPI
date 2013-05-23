package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlValues {

    @XmlElement
    private Date time;

    @XmlElement(name="value")
    private List<Float> value;

    public XmlValues(Date time){
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Float> getValueList() {
        return value;
    }

    public void setValueList(List<Float> value) {
        this.value = value;
    }
}
