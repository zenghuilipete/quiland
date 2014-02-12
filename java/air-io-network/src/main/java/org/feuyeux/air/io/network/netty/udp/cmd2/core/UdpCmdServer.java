package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

public class UdpCmdServer {
    private static final Logger logger = LogManager.getLogger(UdpCmdServer.class.getName());
    final int nettyPort;

    public UdpCmdServer() {
        this.nettyPort = ENV.NETTY_PORT;
    }

    public UdpCmdServer(int nettyPort) {
        this.nettyPort = nettyPort;
    }

    public void init() throws InterruptedException {
        init(true);
    }

    public void init(boolean durable) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        protected void initChannel(DatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    // new LoggingHandler(LogLevel.INFO),
                                    new UdpCmdServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(nettyPort).sync();
            logger.debug("UDP Command Server launched.");

            if (durable) {
                f.channel().closeFuture().sync();
            } else {
                if (!f.channel().closeFuture().await(ENV.CONNECT_TIMEOUT)) {
                    logger.info("UDP Command Server closed.");
                }
            }

        } finally {
            group.shutdownGracefully();
        }
    }
}
