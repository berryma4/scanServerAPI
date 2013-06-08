package edu.msu.frib.scanserver.api;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;

import edu.msu.frib.scanserver.common.commands.XmlCommandSet;

public class ScanServerClientUT extends AbstractScanServerClientTest {

	protected static Pattern SCAN_PATH_PATTERN = Pattern.compile("/scan/([^/]+)");
	
	protected static Pattern SCAN_DATA_PATH_PATTERN = Pattern.compile("/scan/([^/]+)/data");
	
	protected static Pattern SCAN_CMDS_PATH_PATTERN = Pattern.compile("/scan/([^/]+)/commands");	
	

	
	protected static String TEST_SCAN_ONE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                                                "<scan>\n" +
                                                "  <id>%d</id>\n" +
                                                "  <name>" + TEST_SCAN_CMDS_ONE_NAME + "</name>\n" +
                                                "  <created>1370641269356</created>\n" +
                                                "  <state>Finished</state>\n" +
                                                "  <runtime>1000</runtime>\n" +
                                                "  <percentage>0</percentage>\n" +
                                                "  <finish>1370641270356</finish>\n" +
                                                "  <command/>\n" +
                                                "</scan>\n";
	
	protected static String TEST_SCAN_CMDS_ONE_XML	= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                                                  "<commands>\n" +
	                                                  "  <loop>\n" +
	                                                  "    <address>0</address>\n" +
	                                                  "    <device>loc://positioner</device>\n" +
	                                                  "    <start>0.0</start>\n" +
	                                                  "    <end>10.0</end>\n" +
	                                                  "    <step>1.0</step>\n" +
	                                                  "    <wait>false</wait>\n" +
	                                                  "    <tolerance>0.1</tolerance>\n" +
	                                                  "    <body>\n" +
	                                                  "      <log>\n" +
	                                                  "        <address>1</address>\n" +
	                                                  "        <devices>\n" +
	                                                  "          <device>sim://gaussianNoise</device>\n" +
	                                                  "        </devices>\n" +
	                                                  "      </log>\n" +
	                                                  "    </body>\n" +
	                                                  "  </loop>\n" +
	                                                  "</commands>\n";
	
	protected static String TEST_SCAN_DATA_ONE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                                                 "<data>\n" +
	                                                 "  <devices>\n" +
	                                                 "    <device>sim://gaussianNoise</device>\n" +
	                                                 "  </devices>\n" +
	                                                 "  <samples>\n" +
	                                                 "    <values>\n" +
	                                                 "      <time>1370641270356</time>\n" +
	                                                 "      <value>1.0</value>\n" +
	                                                 "    </values>\n" +
	                                                 "    <values>\n" +
	                                                 "      <time>1370641270364</time>\n" +
	                                                 "      <value>2.0</value>\n" +
	                                                 "    </values>\n" +
	                                                 "    <values>\n" +
	                                                 "      <time>1370641270368</time>\n" +
	                                                 "      <value>3.0</value>\n" +
	                                                 "    </values>\n" +
	                                                 "  </samples>\n" +
	                                                 "</data>\n";
	
	protected static String TEST_SCAN_ID_XML = "<id>%d</id>\n";
	
	
	private static TestScanServerHandler handler = new TestScanServerHandler();
	
	private static ScanServerClient client = ScanServerClientImpl.SSCBuilder.serviceURL("http://localhost:4812").withClient(handler.getClient()).create();


	@Override
	public ScanServerClient getScanServerClient() {
		return client;
	}

	
	private static class TestScanServerHandler extends AbstractClientHandler {
		
		private Client client = new Client(this);
		
		private Map<Long,String> scans = new LinkedHashMap<Long,String>();
		private Map<Long,String> scanData = new LinkedHashMap<Long,String>();
		private Map<Long,String> scanCmds = new LinkedHashMap<Long,String>();
		
		private long nextScanId = 1;
		
		@Override
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
			
			printRequest(null);
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int responseStatus = 200;
			String responseString = scans.get(id);
			if( responseString == null ) {
				responseStatus = 500;
				responseString = "";
			}
			
			InputStream responseStream = new ByteArrayInputStream(responseString.getBytes());
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);
			
			printResponse(responseString);
			return new ClientResponse(responseStatus, headers, responseStream, client.getMessageBodyWorkers());
		}
		
		
		protected ClientResponse getScanData(String name, ClientRequest request) {
			
			printRequest(null);
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int responseStatus = 200;
			String responseString = scanData.get(id);
			if( responseString == null ) {
				responseStatus = 500;
				responseString = "";
			} 
			
			InputStream responseStream = new ByteArrayInputStream(responseString.getBytes());
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);			
			
			printResponse(responseString);
			return new ClientResponse(responseStatus, headers, responseStream, client.getMessageBodyWorkers());
		}
		
		protected ClientResponse getScanCommands(String name, ClientRequest request) {
			
			printRequest(null);
			
			Long id;
			try {
				id = Long.parseLong(name);
			}
			catch(NumberFormatException e) {
				id = 0L;
			}
			
			int responseStatus = 200;
			String responseString = scanCmds.get(id);
			if( responseString == null ) {
				responseStatus = 500;
				responseString = "";
			}
			
			InputStream responseStream = new ByteArrayInputStream(responseString.getBytes());
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);
			
			printResponse(responseString);
			return new ClientResponse(responseStatus, headers, responseStream, client.getMessageBodyWorkers());
		}
		
		protected ClientResponse queueScan(String name, ClientRequest request) {
			
			Object entity = request.getEntity();
			assertNotNull(entity);
			assertSame(XmlCommandSet.class, entity.getClass());
			XmlCommandSet commandSet = (XmlCommandSet)entity;
			printRequest(toXml(commandSet, XmlCommandSet.class, request.getHeaders()));
			
			Long id = nextScanId;
			nextScanId += 1;
			
			if( name.equals(TEST_SCAN_CMDS_ONE_NAME) ) {
				scans.put(id, String.format(TEST_SCAN_ONE_XML, id));
				scanCmds.put(id, TEST_SCAN_CMDS_ONE_XML);
				scanData.put(id, TEST_SCAN_DATA_ONE_XML);
			} else {
				throw new ClientHandlerException("Queue scan unexpected scan name: " + name);
			}
			
			String responseString = String.format(TEST_SCAN_ID_XML, id);
			InputStream responseStream = new ByteArrayInputStream(responseString.getBytes());
			
			InBoundHeaders headers = new InBoundHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_XML);
			
			printResponse(responseString);
			return new ClientResponse(200, headers, responseStream, client.getMessageBodyWorkers());
		}
		
	}
}
