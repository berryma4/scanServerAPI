package edu.msu.frib.scanserver.api;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sun.jersey.api.client.WebResource;

import edu.msu.frib.scanserver.common.XmlScan;
import edu.msu.frib.scanserver.common.XmlScans;
import edu.msu.frib.scanserver.common.ScanState;
import edu.msu.frib.scanserver.api.ScanServerClientImpl;

public class ScanServerClientTest {

	private XmlScan xmlScan0001 = new XmlScan(1L, "test1", new Date(), ScanState.Aborted, new Date(), 100L, new Date(), "CMD", "");
	private XmlScan xmlScan0002 = new XmlScan(2L, "test2", new Date(), ScanState.Aborted, new Date(), 100L, new Date(), "CMD", "");
	
	private List<XmlScan> xmlScanList = Arrays.asList( xmlScan0001, xmlScan0002 );
	
	
	private WebResource service = null;
	private ScanServerClient client = null;
	
	
	public ScanServerClientTest() {
		
		service = mock(WebResource.class);
		when(service.getURI()).thenReturn(URI.create("http://mock.test.com"));
		
		// Initialize mock route for '/scan'
		WebResource scanService = mock(WebResource.class);
		when(service.path("scan")).thenReturn(scanService);
		
		// Initialize mock route for '/scan/1'
		WebResource scan0001Service = mock(WebResource.class);
		when(scanService.path(String.valueOf(xmlScan0001.getId()))).thenReturn(scan0001Service);
		
		// Initialize mock route for 'GET /scan/1'
		WebResource.Builder xmlScan0001Service = mock(WebResource.Builder.class);
		when(scan0001Service.accept(MediaType.APPLICATION_XML)).thenReturn(xmlScan0001Service);
		
		when(xmlScan0001Service.get(XmlScan.class)).thenReturn(xmlScan0001);
		
		// Initialize mock route for '/scan/2'
		WebResource scan0002Service = mock(WebResource.class);
		when(scanService.path(String.valueOf(xmlScan0002.getId()))).thenReturn(scan0002Service);
		
		// Initialize mock route for 'GET /scan/2'
		WebResource.Builder xmlScan0002Service = mock(WebResource.Builder.class);
		when(scan0002Service.accept(MediaType.APPLICATION_XML)).thenReturn(xmlScan0002Service);
		
		when(xmlScan0002Service.get(XmlScan.class)).thenReturn(xmlScan0002);
		
		// Initialize mock route for 'GET /scans'
		WebResource scansService = mock(WebResource.class);
		when(service.path("scans")).thenReturn(scansService);
				
		WebResource.Builder mXmlScansService = mock(WebResource.Builder.class);
		when(scansService.accept(MediaType.APPLICATION_XML)).thenReturn(mXmlScansService);
		
		when(mXmlScansService.get(XmlScans.class)).thenReturn(new XmlScans(xmlScanList));
		
		// Initialize the client
		client = ScanServerClientImpl.SSCBuilder.service(service).create();
	}
	
	@Test
	public void getAllScans() {
		List<Scan> scans = client.getAllScans();		
		assertTrue("scan list is unexpected size", scans.size() == xmlScanList.size());
		for( int idx=0; idx<scans.size(); idx++ ) {
			assertTrue("scan is unequal to test data", scans.get(idx).equals(new Scan(xmlScanList.get(idx))));
		}
	}
	
	@Test
	public void getScan0001() {
		Scan scan = client.getScan(1L);
		assertTrue("scan 0001 is unequal to test data", scan.equals(new Scan(xmlScan0001)));
	}
	
	@Test
	public void getScan0002() {
		Scan scan = client.getScan(2L);
		assertTrue("scan 0002 is unequal to test data", scan.equals(new Scan(xmlScan0002)));
	}
	
	@Test
	public void getScan0003() {
		//What is the proper response for requesting a scan that doesn't exist??
	}
}
