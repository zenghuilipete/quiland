package org.feuyeux.air.io.network.nio.mina.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.feuyeux.air.io.network.nio.mina.MinaTimeClient;
import org.feuyeux.air.io.network.nio.mina.TimeServerHandler;

public class MinaUDPTimeClient extends MinaTimeClient {
    public MinaUDPTimeClient() {
        logger = LogManager.getLogger(TimeServerHandler.class);
        connector = new NioDatagramConnector();
    }

    public static void main(String[] args) throws Throwable {
        new MinaUDPTimeClient().connect();
    }
}
