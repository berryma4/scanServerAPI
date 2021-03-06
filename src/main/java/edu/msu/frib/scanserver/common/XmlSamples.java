package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */

@XmlRootElement(name = "samples")
public class XmlSamples {

    private List<XmlValues> values;

    @XmlElement(name="values")
    public List<XmlValues> getXmlValues() {
        return values;
    }

    public void setXmlValues(List<XmlValues> values) {
        this.values = values;
    }
}
