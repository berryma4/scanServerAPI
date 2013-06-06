package edu.msu.frib.scanserver.api;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.msu.frib.scanserver.api.commands.CommandComposite;
import edu.msu.frib.scanserver.api.commands.CommandSet;
import edu.msu.frib.scanserver.api.commands.LogCommand;
import edu.msu.frib.scanserver.api.commands.LoopCommand;


public abstract class AbstractScanServerClientTest {

	protected static String TEST_COMMAND_ONE_NAME = "SimGaussTest1";
	
	protected static CommandSet TEST_COMMAND_ONE;
	
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

		TEST_COMMAND_ONE = CommandSet.builder().add(loopCmd).build();
	}
	

	protected abstract ScanServerClient getScanServerClient();
	
	
	@Test
	public void queueScan() {
				
		ScanServerClient client = getScanServerClient();
		
		Long id = client.queueScan(TEST_COMMAND_ONE_NAME, TEST_COMMAND_ONE);
		assertNotNull(id);
		assertTrue(id > 0);
		
		Scan scan = client.getScan(id);
		assertNotNull(scan);
		assertEquals(TEST_COMMAND_ONE_NAME, scan.getName());
		
		CommandComposite commands = client.getScanCommands(id);
		System.out.println(commands);
		System.out.println(commands.getCommands());
		assertEquals(TEST_COMMAND_ONE, commands);
		
		Data data = client.getScanData(id);
		System.out.println(data);
		System.out.println(data.getNames());
		System.out.println(data.getValues());
		//Should assert something, not sure what data to expect?
	}
	
	
	
	
	
}
