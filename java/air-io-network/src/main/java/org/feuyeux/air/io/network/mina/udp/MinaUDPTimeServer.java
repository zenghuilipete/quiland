package org.feuyeux.air.io.network.mina.udp;

import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.feuyeux.air.io.network.mina.MinaTimeServer;

import java.io.IOException;

public class MinaUDPTimeServer extends MinaTimeServer {
    public MinaUDPTimeServer() {
        super(new NioDatagramAcceptor());
        ((NioDatagramAcceptor) acceptor).getSessionConfig().setReuseAddress(true);
    }

    public static void main(String[] args) throws IOException {
        new MinaUDPTimeServer().init();
    }
}