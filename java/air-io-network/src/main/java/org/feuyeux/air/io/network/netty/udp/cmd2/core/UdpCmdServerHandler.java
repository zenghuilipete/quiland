package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.netty.udp.cmd2.controller.Controller;
import org.feuyeux.air.io.network.netty.udp.cmd2.controller.ControllerFactory;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;

public class UdpCmdServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger logger = LogManager.getLogger(UdpCmdServerHandler.class.getName());

    public UdpCmdServerHandler() {
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        logger.debug("DatagramPacket:{}", datagramPacket);
        UdpCommand udpCommand = UdpCmdCodec.decode(datagramPacket.content());
        logger.debug("UDP Command Client response:{}", udpCommand);
        Controller controller = ControllerFactory.getInstance(udpCommand.getType());
        controller.process(udpCommand.getControlInfo());

        ctx.write(new DatagramPacket(
                Unpooled.copiedBuffer("handled:" + udpCommand, CharsetUtil.UTF_8), datagramPacket.sender()));
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
