package edu.msu.frib.scanserver.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.epics.util.time.TimeDuration;
import org.epics.util.time.Timestamp;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;

import edu.msu.frib.scanserver.common.ScanState;
import edu.msu.frib.scanserver.common.XmlData;
import edu.msu.frib.scanserver.common.XmlDevice;
import edu.msu.frib.scanserver.common.XmlId;
import edu.msu.frib.scanserver.common.XmlSamples;
import edu.msu.frib.scanserver.common.XmlScan;
import edu.msu.frib.scanserver.common.XmlValues;
import edu.msu.frib.scanserver.common.commands.XmlCommand;
import edu.msu.frib.scanserver.common.commands.XmlCommandSet;
import edu.msu.frib.scanserver.common.commands.XmlLogCommand;
import edu.msu.frib.scanserver.common.commands.XmlLoopCommand;

public class ScanServerClientUT extends AbstractScanServerClientTest {

	protected static Pattern SCAN_PATH_PATTERN = Pattern.compile("/scan/([^/]+)");
	
	protected static Pattern SCAN_DATA_PATH_PATTERN = Pattern.compile("/scan/([^/]+)/data");
	
	protected static Pattern SCAN_CMDS_PATH_PATTERN = Pattern.compile("/scan/([^/]+)/commands");
	
	protected static String TEST_COMMAND_ONE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + 
	                                               "<commands>" +
	                                                   "<loop>" + 
	                                                       "<address>0</address>" +
	                                                       "<body>" +
	                                                           "<log>" +
	                                                           "<address>1</address>" +
	                                                           "<devices>" +
	                                                               "<device>sim://gaussianNoise</device>" +
	                                                           "</devices>" +
	                                                           "<devices>" +
	                                                               "<device>loc://positioner</device>" +	 
	                                                           "</devices>" +
	                                                           "</log>" +
	                                                       "</body>" +
	                                                       "<device>loc://positioner</device>" +
	                                                       "<end>10.0</end>" +
	                                                       "<readback></readback>" +
	                                                       "<start>0.0</start>" +
	                                                       "<step>1.0</step>" +
	                                                       "<timeout>0.0</timeout>" +
	                                                       "<tolerance>0.1</tolerance>" +
	                                                       "<wait>false</wait>" +
	                                                   "</loop>" +
	                                               "</commands>";

	
	private static TestScanServerHandler handler = new TestScanServerHandler();
	
	private static ScanServerClient client = ScanServerClientImpl.SSCBuilder.serviceURL("http://localhost:4812").withClient(handler.getClient()).create();


	@Override
	public ScanServerClient getScanServerClient() {
		return client;
	}

	
	private static class TestScanServerHandler implements ClientHandler {
		
		private Client client = new Client(this);
		
		private Map<Long,XmlScan> scans = new LinkedHashMap<Long,XmlScan>();
		private Map<Long,XmlData> scanData = new LinkedHashMap<Long,XmlData>();
		private Map<Long,XmlCommandSet> scanCmds = new LinkedHashMap<Long,XmlCommandSet>();
		
		private long nextScanId = 1;
		private boolean firstQueue = true;
		
		public Client getClient() {
			return client;
		}

		@Override
		public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
			
			String method = request.getMethod();
			String path = request.getURI().getPath();
			
			if( path.startsWith("/scans")) {
				return null;
			}
			
			Matcher scanPathMatcher = SCAN_PATH_PATTERN.matcher(path);
			if( scanPathMatcher.matches() ) {
				if( method.equalsIgnoreCase("GET") ) {
					return getScan(scanPathMatcher.group(1), request);
				}
				if( method.equalsIgnoreCase("POST")) {
					return queueScan(scanPathMatcher.group(1), request);
				}
			}
			
			Matcher scanDataPathMatcher = SCAN_DATA_PATH_PATTERN.matcher(path);
			if( scanDataPathMatcher.matches() ) {
				if( method.equalsIgnoreCase("GET") ) {
					return getScanData(scanDataPathMatcher.group(1), request);
				}
			}
			
			Matcher scanCmdsPathMatcher = SCAN_CMDS_PATH_PATTERN.matcher(path);
			if( scanCmdsPathMatcher.matches() ) {
				if( method.equalsIgnoreCase("GET") ) {
					return getScanCommands(scanCmdsPathMatcher.group(1), request);
				}
			}
			
			throw new AssertionError("Test scan server unexpected request path");
		}
		
		protected ClientResponse getScan(String name, ClientRequest request) {
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int status;
			InputStream data;
			XmlScan scan = scans.get(id);
			if( scan != null ) {
				if( scan.getState() == ScanState.Running ) {
					Timestamp now = Timestamp.now();
					scan.setRuntime(now.durationBetween(scan.getCreated()));
				}
				status = 200;
				data = new ByteArrayInputStream(toXml(scan, XmlScan.class, request.getHeaders()));
			} else {
				status = 500;
				data = new ByteArrayInputStream(new byte[0]);
			}
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);			
			return new ClientResponse(status, headers, data, client.getMessageBodyWorkers());
		}
		
		
		protected ClientResponse getScanData(String name, ClientRequest request) {
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int status;
			InputStream response;
			XmlData data = scanData.get(id);
			if( data != null ) {
				status = 200;
				response = new ByteArrayInputStream(toXml(data, XmlData.class, request.getHeaders()));
			} else {
				status = 500;
				response = new ByteArrayInputStream(new byte[0]);
			}
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);			
			return new ClientResponse(status, headers, response, client.getMessageBodyWorkers());
		}
		
		protected ClientResponse getScanCommands(String name, ClientRequest request) {
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int status;
			InputStream response;
			XmlCommandSet cmds = scanCmds.get(id);
			if( cmds != null ) {
				status = 200;
				response = new ByteArrayInputStream(toXml(cmds, XmlCommandSet.class, request.getHeaders()));
			} else {
				status = 500;
				response = new ByteArrayInputStream(new byte[0]);
			}
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);			
			return new ClientResponse(status, headers, response, client.getMessageBodyWorkers());
		}
		
		protected ClientResponse queueScan(String name, ClientRequest request) {
			
			Object entity = request.getEntity();
			assertNotNull(entity);
			assertSame(XmlCommandSet.class, entity.getClass());
			XmlCommandSet commandSet = (XmlCommandSet)entity;
			
			if( name.equals(TEST_COMMAND_ONE_NAME) ) {
				String xmlCommandSet = new String(toXml(commandSet, XmlCommandSet.class, request.getHeaders()));
				assertEquals(xmlCommandSet, TEST_COMMAND_ONE_XML);
			}
		
			XmlId id = new XmlId();
			id.setId(nextScanId);
			nextScanId += 1;
			
			scanCmds.put(id.getId(), commandSet);
			
			ScanState state = ScanState.Paused;
			if( firstQueue ) {
				state = ScanState.Running;
				firstQueue = false;
			}
			
			Timestamp now = Timestamp.now();
			TimeDuration duration = now.durationBetween(now);
			
			scans.put(id.getId(), new XmlScan(id.getId(), name, now, state, duration, 0L, now, "CMD", ""));
			
			List<String> devices = new ArrayList<String>();
			
			// Discover all the logged devices in CommandSet.
			Queue<XmlCommand> commands = new LinkedList<XmlCommand>(commandSet.getCommands());
			while(!commands.isEmpty()) {
				XmlCommand command = commands.poll();
				if( command instanceof XmlCommandSet ) {
					commands.addAll(((XmlCommandSet)command).getCommands());
				} else if( command instanceof XmlLoopCommand ) {
					commands.addAll(((XmlLoopCommand)command).getBody());
				} else if( command instanceof XmlLogCommand ) {
					for(XmlDevice device : ((XmlLogCommand)command).getDevices()) {
						devices.add(device.getDevice());
					}
				}
			}
			
			System.out.println(devices);
			
			XmlData data = new XmlData();
			data.setXmlDeviceList(devices);
			
			XmlSamples samples = new XmlSamples();
			data.setSamples(samples);
			
			List<XmlValues> values = new ArrayList<XmlValues>();
			samples.setXmlValues(values);
			
			for(float v=0.0F; v<10.0F; v += 1.0) {
				XmlValues value = new XmlValues();
				value.setTime(Timestamp.now());
				value.setValueList(new ArrayList<Float>());
				for(int i=0; i<devices.size(); i++) {
					value.getValueList().add(v);
				}
				values.add(value);
			}
			
			scanData.put(id.getId(), data);
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);
				
			InputStream response = new ByteArrayInputStream(toXml(id, XmlId.class, request.getHeaders()));
			
			return new ClientResponse(200, headers, response, client.getMessageBodyWorkers());
		}
		
		
		private <T> byte[] toXml(T t, Class<T> type, MultivaluedMap<String,Object> headers) {
			
			Client client = getClient();
			Annotation[] annotations = new Annotation[0];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			MessageBodyWriter<T> mbw = client.getMessageBodyWorkers().getMessageBodyWriter(type, null, annotations, MediaType.APPLICATION_XML_TYPE);
			assertNotNull("No MessageBodyWriter found for media type application/xml", mbw);
			
			try {
				mbw.writeTo(t, type, null, annotations, MediaType.APPLICATION_XML_TYPE, headers, output);
			}
			catch(IOException e) {
				throw new AssertionError("Error will writing object to XML", e);
			}
			
			return output.toByteArray();
		}	
	}
}
  