package org.feuyeux.air.io.network.netty.udp.cmd2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdClient;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdServer;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class KeyCommandTest {
    private final static Logger logger = LogManager.getLogger(KeyCommandTest.class);

    @Test
    public void testTcpCommunication() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        final UdpCmdServer server = new UdpCmdServer();
        final UdpCmdClient client = new UdpCmdClient();

        ExecutorService e = Executors.newFixedThreadPool(2);

        Collection<Callable<String>> tasks = new ArrayList<>();
        tasks.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:13"));
                client.send(keyCommand);
                return "Client test DONE";
            }
        });

        tasks.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    server.init(false);
                } catch (Exception e) {
                    logger.error(e);
                    return "Server test Failed.";
                }
                return "Server test DONE";
            }
        });

        List<Future<String>> futures = e.invokeAll(tasks);

        for (int i = 0; i < futures.size(); i++) {
            String result = futures.get(i).get(20, TimeUnit.SECONDS);
            logger.debug(result);
        }
        e.shutdown();
    }
}