package org.feuyeux.air.io.network.netty.udp.cmd2;

import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdClient;
import org.feuyeux.air.io.network.netty.udp.cmd2.core.UdpCmdServer;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 *
 * Created by feuyeux@gmail.com
 * Date: Feb 12 2014
 * Time: 10:50 AM
 */
public class CommunicationTest {

    @Test
    public void testServer() throws InterruptedException {
        new UdpCmdServer(9876).init();
    }

    @Test
    public void testSend() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client = new UdpCmdClient("10.11.72.69", 9876);
        UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:13"));
        client.send(keyCommand);
        keyCommand.setControlInfo(new KeyControlInfo("KEY:10"));
        client.send(keyCommand);
    }

    @Test
    public void testBroadcast() throws InterruptedException, TimeoutException, ExecutionException {
        final UdpCmdClient client = new UdpCmdClient("10.11.72.69", 9876);
        UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:13"));
        client.broadcast(keyCommand);
        keyCommand.setControlInfo(new KeyControlInfo("KEY:10"));
        client.broadcast(keyCommand);
    }

    @Test
    public void testSendPerformance() throws InterruptedException, TimeoutException, ExecutionException {
        for (int i = 0; i < 10000; i++) {
            final UdpCmdClient client = new UdpCmdClient("10.11.72.69", 9876);
            UdpCommand keyCommand = new UdpCommand(CommandType.KEY, new KeyControlInfo("KEY:" + i));
            client.send(keyCommand);
            Thread.sleep(50);
        }
    }
}
