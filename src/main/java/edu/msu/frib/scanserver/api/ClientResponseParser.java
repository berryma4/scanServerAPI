package edu.msu.frib.scanserver.api;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientResponseParser extends ParserCallback {
    private String message = "";
    boolean messageFlag = false;
    boolean causeFlag = false;

    public ClientResponseParser() {
        super();
    }

    public void handleText(char[] data, int pos) {
        String strData = new String(data);
        if(messageFlag){
            message += strData;
            messageFlag = false;
        }else if(causeFlag) {
            message += " - " + strData;
            causeFlag = false;
        }else {
            if(strData.equalsIgnoreCase("description"))
                messageFlag = true;
            else if(strData.equalsIgnoreCase("caused by:"))
                causeFlag = true;
        }
    }

    public void handleStartTag(Tag t, MutableAttributeSet a, int p) {
    }

    public String getMessage() {
        return this.message;
    }
}
