package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class UdpCmdClient {
    private static final Logger logger = LogManager.getLogger(UdpCmdClient.class.getName());
    private static UdpCmdClient instance;
    private static UdpCmdClientContext context;

    final String serverIp;
    final int nettyPort;
    private EventLoopGroup group;
    private Channel channel;

    UdpCmdClient() {
        serverIp = ENV.SERVER_IP;
        nettyPort = ENV.NETTY_PORT;
    }

    UdpCmdClient(String serverIp, int nettyPort, int type) throws InterruptedException {
        this.serverIp = serverIp;
        this.nettyPort = nettyPort;
        group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        final UdpCmdClientHandler clientHandler = new UdpCmdClientHandler(context);
        b.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    public void initChannel(DatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                //new LoggingHandler(LogLevel.INFO),
                                clientHandler
                        );
                    }
                });
        if (type > 0) {
            channel = b.connect(serverIp, nettyPort).sync().channel();
        } else {
            channel = b.bind(new InetSocketAddress(0)).sync().channel();
        }
    }

    public static synchronized UdpCmdClient getUdpClient(String serverIp, int nettyPort) throws InterruptedException {
        if (serverIp == null || serverIp.isEmpty()) {
            return null;
        }
        if (instance == null) {
            if (context == null) {
                context = new UdpCmdClientContext();
            }
            instance = new UdpCmdClient(serverIp, nettyPort, 1);
        }
        return instance;
    }

    public static synchronized UdpCmdClient getBroadcastClient(int nettyPort) throws InterruptedException {
        if (instance == null) {
            context = new UdpCmdClientContext();
            instance = new UdpCmdClient(null, nettyPort, 0);
        }
        return instance;
    }

    public void broadcast(String message) {
        write(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8), message);
    }

    public void send(UdpCommand udpCommand) {
        ByteBuf data = UdpCmdCodec.encode(udpCommand);
        write(data, udpCommand.toString());
    }

    private void write(ByteBuf data, String descrption) {
        String receiver = serverIp == null ? "255.255.255.255" : serverIp;
        DatagramPacket udpPacket = new DatagramPacket(data, new InetSocketAddress(receiver, nettyPort));
        ChannelFuture cf = channel.writeAndFlush(udpPacket);
        logger.debug("UDP Command[{}] has been send.", descrption);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.debug("UDP Command has been received on Server.");
                } else {
                    logger.error("UDP Command failed during the transports", future.cause());
                }
            }
        });
    }

    public String[] getServers(long timeout, TimeUnit unit) throws InterruptedException {
        String[] servers = context.getServers(timeout, unit);
        if (servers == null) {
            return new String[]{""};
        } else {
            return servers;
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }
}
