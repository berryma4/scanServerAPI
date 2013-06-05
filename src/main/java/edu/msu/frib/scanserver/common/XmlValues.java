package edu.msu.frib.scanserver.common;

import org.epics.util.time.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    private Timestamp time;
    private List<Float> value;

    public XmlValues(){
    }

    @XmlElement
    @XmlJavaTypeAdapter(TimestampStringAdapter.class)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @XmlElement(name="value")
    public List<Float> getValueList() {
        return value;
    }

    public void setValueList(List<Float> value) {
        this.value = value;
    }
}
