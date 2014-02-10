package org.feuyeux.air.io.network.udpctrl.udphandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by erichan on 2/10/14.
 */
public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final static Logger logger = LogManager.getLogger(ClientHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        logger.debug("{} received at {}", response, System.currentTimeMillis());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause);
        ctx.close();
    }
}
