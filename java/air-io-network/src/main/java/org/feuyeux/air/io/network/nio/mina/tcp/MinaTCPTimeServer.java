package org.feuyeux.air.io.network.nio.mina.tcp;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.feuyeux.air.io.network.nio.mina.MinaTimeServer;

import java.io.IOException;

public class MinaTCPTimeServer extends MinaTimeServer {
    public MinaTCPTimeServer() {
        acceptor = new NioSocketAcceptor();
    }

    public static void main(String[] args) throws IOException {
        new MinaTCPTimeServer().init();
    }
}