package org.feuyeux.air.io.network.netty.udp.cmd2.core;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UdpCmdClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final static Logger logger = LogManager.getLogger(UdpCmdClientHandler.class);

    public UdpCmdClientHandler() {
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        logger.debug("DatagramPacket:{}", datagramPacket);
        ByteBuf content = datagramPacket.content();
        logger.debug("UDP Command Server response:{}", content.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
