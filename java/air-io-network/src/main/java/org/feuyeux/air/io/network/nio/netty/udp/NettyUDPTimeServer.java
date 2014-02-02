package org.feuyeux.air.io.network.nio.netty.udp;

import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.feuyeux.air.io.network.nio.netty.NettyTimeServer;
import org.feuyeux.air.io.network.nio.netty.tcp.NettyTCPTimeServer;

public class NettyUDPTimeServer extends NettyTimeServer {
    public NettyUDPTimeServer() {
        channelClass = NioDatagramChannel.class;
    }

    public static void main(String[] args) throws Exception {
        new NettyTCPTimeServer().init();
    }
}
