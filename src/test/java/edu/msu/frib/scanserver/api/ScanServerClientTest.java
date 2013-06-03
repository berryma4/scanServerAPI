package edu.msu.frib.scanserver.api;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import edu.msu.frib.scanserver.api.ScanServerClientImpl;

import com.sun.jersey.api.client.WebResource;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import edu.msu.frib.scanserver.common.ScanState;
import edu.msu.frib.scanserver.common.XmlScan;
import edu.msu.frib.scanserver.common.XmlScans;

public class ScanServerClientTest {

	@Test
	public void queueScan() {
		
		System.out.println("Hello Test World!");
		
		XmlScans xmlScans = new XmlScans();
		
		xmlScans.addXmlScan(new XmlScan(1L, "test", new Date(), ScanState.Aborted, new Date(), 100L, new Date(), "TEST", ""));
		
		// Throws an exception with Jersey V1.11 b/c WebResource.Builder is final //
		WebResource.Builder mockGetScansService = mock(WebResource.Builder.class);
		
		when(mockGetScansService.get(XmlScans.class)).thenReturn(xmlScans);
		
		WebResource aService = mock(WebResource.class);
		
		when(aService.accept(MediaType.APPLICATION_XML)).thenReturn(mockGetScansService);
				
		WebResource service = mock(WebResource.class);
		
		when(service.path("scans")).thenReturn(aService);
				
		ScanServerClient ssclient = ScanServerClientImpl.SSCBuilder.service(service).create();
				
		List<Scan> scans = ssclient.getAllScans();
		
		System.out.println("Size: " + scans.size());
		
		for(Scan scan : scans) {
			System.out.println(scan);
		}
		
		
		//WebResource.Builder mockWebResourceBuilder = mock(WebResource.Builder.class);
		
		
		//WebResource mockScansService = mock(WebResource.class);
		
		//when(mockScansService.path(anyString())).thenReturn(mockService);
		
		
		//WebResource mockService = mock(WebResource.class);
		
		//when(mockService.path("scans")).thenReturn(mockService);

		
		//when(mockService.path("")).thenReturn
		
		
		//when(mockService.path("scan")).thenReturn()
		
		//Long id = client.queueScan("test",commands);
		//assertTrue("scans were not populated for test", client.getAllScans().size() - initialChannelCount == 1);
	}
}
