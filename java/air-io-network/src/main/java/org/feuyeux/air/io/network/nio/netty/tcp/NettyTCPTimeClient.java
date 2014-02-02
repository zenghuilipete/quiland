package org.feuyeux.air.io.network.nio.netty.tcp;

import io.netty.channel.socket.nio.NioSocketChannel;
import org.feuyeux.air.io.network.nio.netty.NettyTimeClient;

public class NettyTCPTimeClient extends NettyTimeClient {
    public NettyTCPTimeClient() {
        channelClass = NioSocketChannel.class;
    }
    public static void main(String[] args) throws Exception {
        new NettyTCPTimeClient().connect();
    }
}
