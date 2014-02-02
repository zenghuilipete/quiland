package org.feuyeux.air.io.network.nio.netty.udp;

import io.netty.channel.socket.nio.NioDatagramChannel;
import org.feuyeux.air.io.network.nio.netty.NettyTimeClient;
import org.feuyeux.air.io.network.nio.netty.tcp.NettyTCPTimeClient;

public class NettyUDPTimeClient extends NettyTimeClient {
    public NettyUDPTimeClient() {
        channelClass = NioDatagramChannel.class;
    }
    public static void main(String[] args) throws Exception {
        new NettyTCPTimeClient().connect();
    }
}
