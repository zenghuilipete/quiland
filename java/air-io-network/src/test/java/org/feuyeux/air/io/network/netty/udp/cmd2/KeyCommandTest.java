package org.feuyeux.air.io.network.netty.udp.cmd2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdClient;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdServer;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class KeyCommandTest {
    private static final Logger logger = LogManager.getLogger(KeyCommandTest.class);

    @Test
    public void testSend() throws Exception {
        logger.debug("Test Send Command");
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(2);
        new Thread() {
            @Override
            public void run() {
                try {
                    final UdpCmdClient client = UdpCmdClient.getUdpClient(ENV.SERVER_IP, ENV.NETTY_PORT);
                    logger.debug("**** client task Waiting for server ****");
                    startGate.await();
                    logger.debug("**** client task Start! ****");
                    for (int i = 0; i < 5; i++) {
                        UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo(String.valueOf(i)));
                        client.send(keyCommand);
                        Thread.sleep(100);
                    }
                    logger.debug("**** client task Done! ****");
                } catch (InterruptedException e) {
                    logger.error(e);
                } finally {
                    endGate.countDown();
                    logger.debug("**** client task countDown! ****");
                }
            }
        }.start();

        final Thread serverTask = new Thread() {
            @Override
            public void run() {
                try {
                    final UdpCmdServer server = new UdpCmdServer(ENV.NETTY_PORT);
                    server.init();
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                try {
                    serverTask.start();
                    logger.debug("**** server task Done! ****");
                    Thread.sleep(500);
                    startGate.countDown();
                } catch (InterruptedException e) {
                    logger.error(e);
                } finally {
                    endGate.countDown();
                    logger.debug("**** server task countDown! ****");
                }
            }
        }.start();
        endGate.await();
    }


    @Test
    public void testBroadcast() throws Exception {
        logger.debug("Test Broadcast Command");
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(2);

        final UdpCmdServer server = new UdpCmdServer();
        final UdpCmdClient client = UdpCmdClient.getBroadcastClient(ENV.NETTY_PORT);

        new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                server.init();
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }
                    }).start();
                    startGate.countDown();
                } catch (Exception e) {
                    logger.error(e);
                    return "Server test Failed.";
                } finally {
                    endGate.countDown();
                }
                return "Server test DONE";
            }
        }.call();

        new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    startGate.await();
                    client.broadcast("hello.");
                    String[] servers = client.getServers(1, TimeUnit.SECONDS);
                    for (int i = 0; i < servers.length; i++) {
                        logger.debug(">>>" + servers[i]);
                    }
                    return "Client test DONE";
                } finally {
                    endGate.countDown();
                }
            }
        }.call();
        endGate.await();
    }
}