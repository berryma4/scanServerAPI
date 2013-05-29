package edu.msu.frib.scanserver.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.sun.jersey.api.client.UniformInterfaceException;
import edu.msu.frib.scanserver.api.commands.CommandComposite;
import edu.msu.frib.scanserver.common.XmlScan;
import edu.msu.frib.scanserver.common.XmlScans;
import edu.msu.frib.scanserver.common.commands.XmlCompositeCommand;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScanServerClientImpl implements ScanServerClient {
    private final WebResource service;
    private final ExecutorService executor;

    private static final String resourceScans = "scans";
    private static final String resourceScan = "scan";
    private static final String resourceCommands = "commands";

    @Override
    public List<Scan> getAllScans() {
        return wrappedSubmit(new Callable<List<Scan>>() {

            @Override
            public List<Scan> call() throws Exception {

                List<Scan> allScans = new ArrayList<Scan>();
                XmlScans allXmlScans = service
                        .path(resourceScans)
                        .accept(MediaType.APPLICATION_XML)
                        .get(XmlScans.class);
                for (XmlScan xmlScan : allXmlScans.getXmlScans()) {
                    allScans.add(new Scan(xmlScan));
                }
                return allScans;
            }

        });
    }

    @Override
    public Scan getScan(Long id) throws ScanServerException {
        return wrappedSubmit(new GetScan(id));
    }
    private class GetScan implements Callable<Scan> {
        private final Long id;

        GetScan(Long id){
            super();
            this.id = id;
        }

        @Override
        public Scan call() throws Exception {
            XmlScan xmlScan = service
                    .path(resourceScan)
                    .path(this.id.toString())
                    .accept(MediaType.APPLICATION_XML)
                    .get(XmlScan.class);
            return new Scan(xmlScan);
        }
    }

    @Override
    public Data getScanData(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CommandComposite getScanCommands(Long id)  throws ScanServerException {
        return wrappedSubmit(new GetScanCommands(id));
    }

    private class GetScanCommands implements Callable<CommandComposite> {
        private final Long id;

        GetScanCommands(Long id){
            super();
            this.id = id;
        }

        @Override
        public CommandComposite call() throws Exception {
            XmlCompositeCommand xmlCompositeCommand = service
                    .path(resourceScan)
                    .path(this.id.toString())
                    .path(resourceCommands)
                    .accept(MediaType.APPLICATION_XML)
                    .get(XmlCompositeCommand.class);
               return null;
        }
    }

    @Override
    public void deleteScan(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long queueScan(String name, CommandComposite commandComposite) {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private <T> T wrappedSubmit(Callable<T> callable) {
        try {
            return this.executor.submit(callable).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() != null
                    && e.getCause() instanceof UniformInterfaceException) {
                throw new ScanServerException(
                        (UniformInterfaceException) e.getCause());
            }
            throw new RuntimeException(e);
        }
    }

    private void wrappedSubmit(Runnable runnable) {
        try {
            this.executor.submit(runnable).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() != null
                    && e.getCause() instanceof UniformInterfaceException) {
                throw new ScanServerException(
                        (UniformInterfaceException) e.getCause());
            }
            throw new RuntimeException(e);
        }
    }

    public static class SSCBuilder {
        // required
        private URI uri = null;

        private ClientConfig clientConfig = null;



        private String protocol = null;

        private ExecutorService executor = Executors.newSingleThreadExecutor();

        private SSCProperties properties = new SSCProperties();

        private static final String serviceURL = "http://localhost:4812"; //$NON-NLS-1$

        private SSCBuilder() {
            this.uri = URI.create(this.properties.getPreferenceValue(
                    "scanServer.serviceURL", serviceURL)); //$NON-NLS-1$
            this.protocol = this.uri.getScheme();
        }

        private SSCBuilder(URI uri) {
            this.uri = uri;
            this.protocol = this.uri.getScheme();
        }

        /**
         * Creates a {@link SSCBuilder} for a CF client to Default URL in the
         * scanServer.properties.
         *
         * @return {@link SSCBuilder}
         */
        public static SSCBuilder serviceURL() {
            return new SSCBuilder();
        }

        /**
         * Creates a {@link SSCBuilder} for a scanServer client to URI <tt>uri</tt>.
         *
         * @param uri
         * @return {@link SSCBuilder}
         */
        public static SSCBuilder serviceURL(String uri) {
            return new SSCBuilder(URI.create(uri));
        }

        /**
         * Creates a {@link SSCBuilder} for a scanServer client to {@link URI}
         * <tt>uri</tt>.
         *
         * @param uri
         * @return {@link SSCBuilder}
         */
        public static SSCBuilder serviceURL(URI uri) {
            return new SSCBuilder(uri);
        }


        /**
         * set the {@link ClientConfig} to be used while creating the
         * channelfinder client connection.
         *
         * @param clientConfig
         * @return {@link SSCBuilder}
         */
        public SSCBuilder withClientConfig(ClientConfig clientConfig) {
            this.clientConfig = clientConfig;
            return this;
        }

        /**
         * Provide your own executor on which the queries are to be made. <br>
         * By default a single threaded executor is used.
         *
         * @param executor
         * @return {@link SSCBuilder}
         */
        public SSCBuilder withExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        /**
         * Will actually create a {@link ScanServerClientImpl} object using
         * the configuration information in this builder.
         *
         * @return {@link ScanServerClientImpl}
         */
        public ScanServerClient create() throws ScanServerException {
            if (this.protocol.equalsIgnoreCase("http")) { //$NON-NLS-1$
                this.clientConfig = new DefaultClientConfig();
            }
            return new ScanServerClientImpl(this.uri, this.clientConfig, this.executor);
        }

        private String ifNullReturnPreferenceValue(String value, String key,
                                                   String Default) {
            if (value == null) {
                return this.properties.getPreferenceValue(key, Default);
            } else {
                return value;
            }
        }
    }
    ScanServerClientImpl(URI uri, ClientConfig config, ExecutorService executor) {
        Client client = Client.create(config);

        client.addFilter(new RawLoggingFilter(Logger
                .getLogger(RawLoggingFilter.class.getName())));
        client.setFollowRedirects(true);
        service = client.resource(UriBuilder.fromUri(uri).build());
        this.executor = executor;
    }
}
