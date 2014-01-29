package org.feuyeux.air.io.network.nio.mina.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.feuyeux.air.io.network.nio.mina.MinaTimeClient;

public class MinaTCPTimeClient extends MinaTimeClient {
    public MinaTCPTimeClient() {
        logger = LogManager.getLogger(MinaTCPTimeClient.class);
        connector = new NioSocketConnector();
    }

    public static void main(String[] args) throws Throwable {
        new MinaTCPTimeClient().connect();
    }
}
