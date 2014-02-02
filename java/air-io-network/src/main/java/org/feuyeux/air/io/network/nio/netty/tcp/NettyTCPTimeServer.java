package org.feuyeux.air.io.network.nio.netty.tcp;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.feuyeux.air.io.network.nio.netty.NettyTimeServer;

public class NettyTCPTimeServer extends NettyTimeServer {
    public NettyTCPTimeServer() {
        channelClass = NioServerSocketChannel.class;
    }

    public static void main(String[] args) throws Exception {
        new NettyTCPTimeServer().init();
    }
}
