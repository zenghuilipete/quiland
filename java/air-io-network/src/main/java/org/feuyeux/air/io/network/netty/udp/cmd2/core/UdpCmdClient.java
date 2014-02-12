package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UdpCmdClient {
    private static final Logger logger = LogManager.getLogger(UdpCmdClient.class.getName());
    final String serverIp;
    final int nettyPort;

    public UdpCmdClient() {
        this.serverIp = ENV.SERVER_IP;
        this.nettyPort = ENV.NETTY_PORT;
    }

    public UdpCmdClient(String serverIp, int nettyPort) {
        this.serverIp = serverIp;
        this.nettyPort = nettyPort;
    }

    public void send(UdpCommand udpCommand) throws InterruptedException, TimeoutException, ExecutionException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
              .channel(NioDatagramChannel.class)
              .handler(new ChannelInitializer<DatagramChannel>() {
                  @Override
                  public void initChannel(DatagramChannel ch) throws Exception {
                      ch.pipeline().addLast(
                        //new LoggingHandler(LogLevel.INFO),
                        new UdpCmdClientHandler());
                  }
              });
            ChannelFuture f = b.connect(serverIp, nettyPort).sync();
            Channel channel = f.channel();
            ByteBuf data = UdpCmdCodec.encode(udpCommand);
            DatagramPacket udpPacket = new DatagramPacket(data,
              new InetSocketAddress(serverIp, nettyPort));
            ChannelFuture cf = channel.writeAndFlush(udpPacket).sync();
            cf.get(ENV.READ_TIMEOUT, TimeUnit.SECONDS);
            logger.debug("UDP Command has been send.");
            if (!cf.channel().closeFuture().await(ENV.CONNECT_TIMEOUT)) {
                logger.info("UDP Command channel closed.");
            }
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public void broadcast(UdpCommand udpCommand) throws InterruptedException, TimeoutException, ExecutionException {
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
                        new UdpCmdClientHandler());
                  }
              });
            Channel channel = b.bind(new InetSocketAddress(0)).sync().channel();
            ByteBuf data = UdpCmdCodec.encode(udpCommand);
            DatagramPacket udpPacket = new DatagramPacket(data,
              new InetSocketAddress(ENV.BROAD_CAST_IP, ENV.NETTY_PORT));
            ChannelFuture cf = channel.writeAndFlush(udpPacket).sync();
            cf.get(ENV.READ_TIMEOUT, TimeUnit.SECONDS);
            logger.debug("UDP Command has been send.");
            if (!cf.channel().closeFuture().await(ENV.CONNECT_TIMEOUT)) {
                logger.info("UDP Command channel closed.");
            }
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
