package org.feuyeux.air.io.network.netty.udp.cmd2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdClient;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdServer;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * Created by feuyeux@gmail.com
 * Date: Feb 12 2014
 * Time: 10:50 AM
 */
public class CommunicationTest {
    private static final Logger logger = LogManager.getLogger(CommunicationTest.class);
    public static final String SERVER_IP = "10.11.72.69";
    public static final int PORT = 9876;
    public static final int LOOP = 10000;
    UdpCmdServer server;

    @Test
    public void testService() {
        try {
            server = new UdpCmdServer(PORT);
            server.init();
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

    @Test
    public void testSend() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client = UdpCmdClient.getUdpClient(SERVER_IP, PORT);
        UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:13"));
        client.send(keyCommand);
        keyCommand.setControlInfo(new KeyControlInfo("KEY:10"));
        client.send(keyCommand);
        Thread.sleep(3000l);
    }

    @Test
    public void testBroadcast() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client = UdpCmdClient.getBroadcastClient(PORT);
        UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:13"));
        client.send(keyCommand);
        keyCommand.setControlInfo(new KeyControlInfo("KEY:10"));
        client.send(keyCommand);
        Thread.sleep(3000l);
    }

    @Test
    public void testSendPerformance() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client = UdpCmdClient.getUdpClient(SERVER_IP, PORT);
        for (int i = 0; i < LOOP; i++) {
            UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:" + i));
            client.send(keyCommand);
            Thread.sleep(50);
        }
    }

    @Test
    public void testBasicFlow() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client0 = UdpCmdClient.getBroadcastClient(PORT);
        client0.broadcast("Hello");
        String[] hosts = client0.getServers(1, TimeUnit.SECONDS);
        UdpCmdClient.clean();

        for (String host : hosts) {
            logger.debug("**** {} ****", host);
            final UdpCmdClient client = UdpCmdClient.getUdpClient(host, PORT);
            if (client != null) {
                UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:X"));
                client.send(keyCommand);
            }
            Thread.sleep(2000l);
        }
        UdpCmdClient.clean();
    }
}
