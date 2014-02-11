package org.feuyeux.air.io.network.bio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.*;

public class TCPCommunicationTest {
    private final static Logger logger = LogManager.getLogger(TCPCommunicationTest.class);

    @Test
    public void testTcpCommunication() throws IOException, InterruptedException, ExecutionException {
        final String message = "tcp-bio-test";

        ExecutorService e = Executors.newFixedThreadPool(1);
        e.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    new BIOTCPServer(ENV.BIO_TCP_PORT);
                } catch (Exception e) {
                    logger.info(e);
                }
            }
        });

        FutureTask<String> f = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                BIOTCPClient client = new BIOTCPClient(ENV.SERVER_IP, ENV.BIO_TCP_PORT);
                return client.sendMessage(message);
            }
        });
        f.run();

        String message2 = f.get();
        logger.info(message2);
        Assert.assertTrue(message2.contains(message));
    }
}