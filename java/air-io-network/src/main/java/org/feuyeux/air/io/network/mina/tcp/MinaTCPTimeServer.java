package org.feuyeux.air.io.network.mina.tcp;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.feuyeux.air.io.network.mina.MinaTimeServer;

import java.io.IOException;

public class MinaTCPTimeServer extends MinaTimeServer {
    public MinaTCPTimeServer() {
        super(new NioSocketAcceptor());
    }

    public static void main(String[] args) throws IOException {
        new MinaTCPTimeServer().init();
    }
}