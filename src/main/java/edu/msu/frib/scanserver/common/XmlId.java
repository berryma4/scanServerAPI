package edu.msu.frib.scanserver.common;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/31/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "")
public class XmlId {
    private long id;

    public XmlId() {
    }

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
