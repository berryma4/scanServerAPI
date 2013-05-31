package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="values")
public class XmlValues {

    private Date time;
    private List<Float> value;

    public XmlValues(Date time){
    }

    @XmlElement
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlElement
    public List<Float> getValueList() {
        return value;
    }

    public void setValueList(List<Float> value) {
        this.value = value;
    }
}
