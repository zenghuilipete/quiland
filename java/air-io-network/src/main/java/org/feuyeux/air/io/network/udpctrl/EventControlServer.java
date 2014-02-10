package org.feuyeux.air.io.network.udpctrl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;
import org.feuyeux.air.io.network.udpctrl.udphandler.ServerHandler;

/**
 * Created by erichan on 2/10/14.
 */
public class EventControlServer {
    private final static Logger logger = LogManager.getLogger(EventControlServer.class);

    public void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
              .channel(NioDatagramChannel.class)
              .option(ChannelOption.SO_BROADCAST, true)
              .handler(new ServerHandler());
            ChannelFuture f = b.bind(AirIO.NETTY_PORT).sync();
            logger.debug("Server launched.");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Failed to launch Receiver.");
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        EventControlServer receiver = new EventControlServer();
        receiver.init();
    }
}
