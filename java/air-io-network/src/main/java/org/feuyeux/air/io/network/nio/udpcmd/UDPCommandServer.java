package org.feuyeux.air.io.network.nio.udpcmd;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

public class UDPCommandServer {
    private static final Logger logger = LogManager.getLogger(UDPCommandServer.class.getName());

    public UDPCommandServer() {
    }
    public void init() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        protected void initChannel(DatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    // new LoggingHandler(LogLevel.INFO),
                                    new DatagramPacketServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(AirIO.NETTY_PORT).sync();
            logger.debug("UDP Command Server launched.");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new UDPCommandServer().init();
    }
}
