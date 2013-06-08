package edu.msu.frib.scanserver.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;

import edu.msu.frib.scanserver.common.commands.XmlCommandSet;

public class ScanServerClientIT extends AbstractScanServerClientTest {

	private static InterceptingClientHandler handler = new InterceptingClientHandler();
	
	private static ScanServerClient client = ScanServerClientImpl.SSCBuilder.serviceURL("http://localhost:4812").withClient(handler.getClient()).create();

	@Override
	protected ScanServerClient getScanServerClient() {
		return client;
	}
	
	
	private static class InterceptingClientHandler extends AbstractClientHandler {

		private Client client = new Client(this);
		
		private Client delegate = Client.create();
		
		@Override
		public Client getClient() {
			return client;
		}
		
		
		@Override
		public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
			
			Object entity = request.getEntity();
			
			if( entity == null ) {
				printRequest(null);
			} else if( entity instanceof XmlCommandSet ) {
				printRequest(toXml((XmlCommandSet)entity, XmlCommandSet.class, request.getHeaders()));
			} else {
				throw new ClientHandlerException("Unable to print request, unexpected entity class");
			}	
			
			ClientResponse response =  delegate.handle(request);
			
			byte[] responseData = readAll(response.getEntityInputStream());
			InputStream responseStream = new ByteArrayInputStream(responseData);
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.putAll(response.getHeaders());
			
			printResponse(new String(responseData));
			return new ClientResponse(response.getStatus(), headers, responseStream, delegate.getMessageBodyWorkers());
		}
		
		
		private byte[] readAll(InputStream input) throws ClientHandlerException {
			int length = 0;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
		
			try {
				while( input.available() > 0 ) {
					length = input.read(buffer);
					if( length > 0 ) { 
						output.write(buffer, 0, length);
					}
				}
			}
			catch(IOException e) {
				throw new ClientHandlerException("Error while reading input stream", e);
			}
			
			return output.toByteArray();
		}
	}
}
