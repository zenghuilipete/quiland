package org.feuyeux.air.io.network.mina.udp;

import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.feuyeux.air.io.network.mina.MinaTimeClient;

public class MinaUDPTimeClient extends MinaTimeClient {
    public MinaUDPTimeClient() {
        super(new NioDatagramConnector());
    }

    public static void main(String[] args) throws Throwable {
        new MinaUDPTimeClient().connect();
    }
}
