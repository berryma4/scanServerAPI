package edu.msu.frib.scanserver.api;

public class ScanServerClientIT extends AbstractScanServerClientTest {

	private static ScanServerClient client = ScanServerClientImpl.SSCBuilder.serviceURL("http://localhost:4812").create();

	@Override
	protected ScanServerClient getScanServerClient() {
		return client;
	}
}
