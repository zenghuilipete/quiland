package org.feuyeux.air.io.network.netty.udp.cmd;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class DatagramPacketServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger logger = LogManager.getLogger(DatagramPacketServerHandler.class.getName());

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        logger.debug("DatagramPacket:{}", datagramPacket);
        UDPCommand udpCommand = UDPCommandCodec.decode(datagramPacket.content());
        logger.debug("UDP Command Client response:{}", udpCommand);
        ctx.write(new DatagramPacket(
                Unpooled.copiedBuffer(new Date().toString(), CharsetUtil.UTF_8), datagramPacket.sender()));
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
