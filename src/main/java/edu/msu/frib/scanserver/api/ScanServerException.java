package edu.msu.frib.scanserver.api;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScanServerException extends RuntimeException {

    private static final long serialVersionUID = 6279123451993808192L;

    private Status status;

    public ScanServerException() {
        super();
    }

    public ScanServerException(String message){
        super(message);
    }

    public ScanServerException(UniformInterfaceException cause) {
        super(parseErrorMsg(cause), cause);
        this.setStatus(Status.fromStatusCode(cause.getResponse().getStatus()));
    }

    private static String parseErrorMsg(UniformInterfaceException ex) {
        String entity = ex.getResponse().getEntity(String.class);
        try {
            ClientResponseParser callback = new ClientResponseParser();
            Reader reader = new StringReader(entity);
            new ParserDelegator().parse(reader, callback, false);
            return callback.getMessage();
        } catch (IOException e) {
            //e.printStackTrace();
            return "Could not retrieve message from server";
        }
    }

    public ScanServerException(Status status, String message) {
        super(message);
        this.setStatus(status);
    }

    /**
     *
     * @param status - the http error status code
     * @param cause - the original UniformInterfaceException
     * @param message - additional error information
     */
    public ScanServerException(Status status, Throwable cause ,String message) {
        super(message, cause);
        this.setStatus(status);
    }

    /**
     * Set the associated HTTP status code which caused this exception.
     *
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the associated HTTP status code which caused this exception.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

}
