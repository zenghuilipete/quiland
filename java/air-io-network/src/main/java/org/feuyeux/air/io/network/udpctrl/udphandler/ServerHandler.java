package org.feuyeux.air.io.network.udpctrl.udphandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlEvent;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlInfo;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlType;
import org.feuyeux.air.io.network.udpctrl.controlinfo.codec.ControlInfoCodecFactory;
import org.feuyeux.air.io.network.udpctrl.controller.Controller;
import org.feuyeux.air.io.network.udpctrl.controller.ControllerFactory;


/**
 * Created by erichan on 2/10/14.
 */
public class ServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final static Logger logger = LogManager.getLogger(ServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        logger.debug(packet);
        ByteBuf buf = packet.content();
        ControlType controlType = ControlType.getInstance(buf.readInt());
        byte[] dst = new byte[10240];
        buf.readBytes(dst);
        ControlInfo controlInfo = ControlInfoCodecFactory.getInstance(controlType).decode(dst);

        ControlEvent controlEvent = new ControlEvent(controlType, controlInfo);

        logger.debug("{} received at {}", controlEvent, System.currentTimeMillis());
        Controller controller = ControllerFactory.getInstance(controlEvent.getControlType());
        controller.process(controlEvent.getControlInfo());

        ctx.write(new DatagramPacket(
          Unpooled.copiedBuffer("handled:" + controlEvent, CharsetUtil.UTF_8), packet.sender()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause);
        // Don't close the channel because we can keep serving requests.
    }
}
