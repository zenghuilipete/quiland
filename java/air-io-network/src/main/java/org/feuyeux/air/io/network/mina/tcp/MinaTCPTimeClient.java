package org.feuyeux.air.io.network.mina.tcp;

import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.feuyeux.air.io.network.mina.MinaTimeClient;

public class MinaTCPTimeClient extends MinaTimeClient {

    public MinaTCPTimeClient() {
        super(new NioSocketConnector());
    }

    public static void main(String[] args) throws Throwable {
        new MinaTCPTimeClient().connect();
    }
}
