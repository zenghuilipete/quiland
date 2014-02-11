package org.feuyeux.air.io.network.netty.udp.cmd;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.net.InetSocketAddress;

public class UDPCommandClient {
    private static final Logger logger = LogManager.getLogger(UDPCommandClient.class.getName());

    public void send(UDPCommand udpCommand) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        public void initChannel(DatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    //new LoggingHandler(LogLevel.INFO),
                                    new DatagramPacketClientHandler());
                        }
                    });
            Channel channel = b.bind(new InetSocketAddress(0)).sync().channel();
            //ByteBuf data = Unpooled.copiedBuffer(udpCommand.toString(), CharsetUtil.UTF_8);
            ByteBuf data = UDPCommandCodec.encode(udpCommand);
            DatagramPacket udpPacket = new DatagramPacket(data,
                    new InetSocketAddress("255.255.255.255", ENV.NETTY_PORT));
            channel.writeAndFlush(udpPacket).sync();
            /*if (!channel.closeFuture().await(5000)) {
                logger.info("Timed out to close the channel.");
            }*/
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new UDPCommandClient().send(new UDPCommand(1, "KEY:13"));
    }
}
