package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/31/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "id")
public class XmlId {
    private long id;

    public XmlId() {
    }

    @XmlValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
