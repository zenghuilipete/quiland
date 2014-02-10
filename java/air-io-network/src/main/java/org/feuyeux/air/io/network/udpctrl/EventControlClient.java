package org.feuyeux.air.io.network.udpctrl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;
import org.feuyeux.air.io.network.temp.QuoteOfTheMomentClientHandler;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlEvent;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlType;
import org.feuyeux.air.io.network.udpctrl.controlinfo.KeyControlInfo;
import org.feuyeux.air.io.network.udpctrl.controlinfo.codec.ControlInfoCodec;
import org.feuyeux.air.io.network.udpctrl.controlinfo.codec.ControlInfoCodecFactory;
import org.feuyeux.air.io.network.udpctrl.udphandler.ClientHandler;

import java.net.InetSocketAddress;


/**
 * Created by erichan on 2/10/14.
 */
public class EventControlClient {
    private final static Logger logger = LogManager.getLogger(EventControlClient.class);
    private final int TIMEOUT = 5000;

    public void send(ControlEvent controlEvent) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group)
              .channel(NioDatagramChannel.class)
              .option(ChannelOption.SO_BROADCAST, true)
              .handler(new ClientHandler());

            Channel ch = b.bind(0).sync().channel();
            send0(controlEvent, ch);

            if (!ch.closeFuture().await(TIMEOUT)) {
                logger.error("Send Control Event timed out.");
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
              .channel(NioDatagramChannel.class)
              .option(ChannelOption.SO_BROADCAST, true)
              .handler(new QuoteOfTheMomentClientHandler());

            Channel ch = b.bind(0).sync().channel();

            // Broadcast the QOTM request to port 8080.
            ch.writeAndFlush(new DatagramPacket(
              Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
              new InetSocketAddress("255.255.255.255", AirIO.MINA_PORT))).sync();

            // QuoteOfTheMomentClientHandler will close the DatagramChannel when a
            // response is received.  If the channel is not closed within 5 seconds,
            // print an error message and quit.
            if (!ch.closeFuture().await(5000)) {
                System.err.println("QOTM request timed out.");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    private void send0(ControlEvent controlEvent, Channel ch) throws InterruptedException {
        ch.writeAndFlush(new DatagramPacket(
          Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
          new InetSocketAddress("255.255.255.255", AirIO.MINA_PORT))).sync();

        ByteBuf buf = getByteBuf(controlEvent);
        ch.writeAndFlush(new DatagramPacket(buf, new InetSocketAddress("255.255.255.255", AirIO.MINA_PORT))).sync();
    }

    private ByteBuf getByteBuf(ControlEvent controlEvent) {
        ControlType controlType = controlEvent.getControlType();
        ControlInfoCodec keyControlInfoCodec = ControlInfoCodecFactory.getInstance(controlType);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(controlType.ordinal());
        buf.writeBytes(keyControlInfoCodec.encode(controlEvent.getControlInfo()));
        return buf;
    }

    public static void main(String[] args) throws Exception {
        EventControlClient eventSender = new EventControlClient();
        eventSender.run();

        eventSender.send(new ControlEvent(ControlType.KEY, new KeyControlInfo("A")));

        eventSender.send(new ControlEvent(ControlType.KEY, new KeyControlInfo("S")));

    }
}
