package edu.msu.frib.scanserver.api;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.epics.util.time.Timestamp;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.ClientHandlerException;

import edu.msu.frib.scanserver.api.commands.CommandComposite;
import edu.msu.frib.scanserver.api.commands.CommandSet;
import edu.msu.frib.scanserver.api.commands.LogCommand;
import edu.msu.frib.scanserver.api.commands.LoopCommand;


public abstract class AbstractScanServerClientTest {

	protected static String TEST_SCAN_CMDS_ONE_NAME = "SimGaussTest1";
	
	protected static CommandSet TEST_SCAN_CMDS_ONE;
	
	static {	
		LogCommand logCmd = LogCommand.builder()
                .address(1)
                .device("sim://gaussianNoise")
                .device("loc://positioner")
                .build();

		LoopCommand loopCmd = LoopCommand.builder()
                   .device("loc://positioner")
                   .start(0.0)
                   .step(1.0)
                   .end(10.0)
                   .tolerance(0.1)
                   .wait(false)
                   .add(logCmd)
                   .build();

		TEST_SCAN_CMDS_ONE = CommandSet.builder().add(loopCmd).build();
	}
	
	protected static Data TEST_SCAN_DATA_ONE;
	
	static {
		List<String> names = new ArrayList<String>();
		names.add("timestamp");
		names.add("sim://gaussianNoise");
		
		List<Class<?>> types = new ArrayList<Class<?>>();
		types.add(Timestamp.class);
		types.add(Float.class);
		types.add(Float.class);
		
		List<Object> values = new ArrayList<Object>();
		values.add(1.0F);
		values.add(2.0F);
		values.add(3.0F);
		
		TEST_SCAN_DATA_ONE = Data.builder().names(names).types(types).values(values).build();
	}
	
	
	protected abstract ScanServerClient getScanServerClient();
	
	
	@Test
	public void queueScan() {
				
		ScanServerClient client = getScanServerClient();
		
		Long id = client.queueScan(TEST_SCAN_CMDS_ONE_NAME, TEST_SCAN_CMDS_ONE);
		assertNotNull(id);
		assertTrue(id > 0);
		
		Scan scan = client.getScan(id);
		assertNotNull(scan);
		assertEquals(TEST_SCAN_CMDS_ONE_NAME, scan.getName());
		
		CommandComposite cmds = client.getScanCommands(id);
		assertNotNull(cmds);
		// Assertion known to fail!
		// CommandSet returned from scan server
		// does SET equal the CommandSet submitted.
		assertEquals(TEST_SCAN_CMDS_ONE, cmds); 
		
		Data data = client.getScanData(id);
		assertNotNull(data);
		assertEquals(TEST_SCAN_DATA_ONE, data);
	}
	
	
	protected static abstract class AbstractClientHandler implements ClientHandler {
		
		public abstract Client getClient();
		
		protected void printRequest(String responseString) {
			System.out.println("==== Request ====");
			System.out.println(responseString);
			System.out.println("==================");
		}
		
		protected void printResponse(String responseString) {
			System.out.println("==== Response ====");
			System.out.println(responseString);
			System.out.println("==================");
		}
		
		protected <T> String toXml(T t, Class<T> type, MultivaluedMap<String,Object> headers) throws ClientHandlerException {
			
			Client client = getClient();
			Annotation[] annotations = new Annotation[0];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			MessageBodyWriter<T> mbw = client.getMessageBodyWorkers().getMessageBodyWriter(type, null, annotations, MediaType.APPLICATION_XML_TYPE);
			assertNotNull("No MessageBodyWriter found for media type application/xml", mbw);
			
			try {
				mbw.writeTo(t, type, null, annotations, MediaType.APPLICATION_XML_TYPE, headers, output);
			}
			catch(IOException e) {
				throw new ClientHandlerException("Error while writing object to XML", e);
			}
			
			return output.toString();
		}
	}
}
