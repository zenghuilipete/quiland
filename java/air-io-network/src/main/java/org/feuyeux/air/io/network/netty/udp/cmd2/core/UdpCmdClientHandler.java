package org.feuyeux.air.io.network.netty.udp.cmd2.core;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class UdpCmdClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger logger = LogManager.getLogger(UdpCmdClientHandler.class);
    private final UdpCmdClientContext context;

    public UdpCmdClientHandler(UdpCmdClientContext context) {
        this.context = context;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        //logger.debug("DatagramPacket:{}", datagramPacket);
        ByteBuf content = datagramPacket.content();
        InetSocketAddress serverHost = datagramPacket.sender();
        String serverIp = serverHost.getAddress().getHostAddress();
        context.addServer(serverIp);
        logger.debug("Server[{} {}] response:'{}'", serverHost.getHostName(), serverIp, content.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Unexpected exception:", cause);
        //ctx.close();
    }
}
