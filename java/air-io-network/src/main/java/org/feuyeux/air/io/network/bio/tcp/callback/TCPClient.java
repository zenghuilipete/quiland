package org.feuyeux.air.io.network.bio.tcp.callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.net.Socket;

public class TCPClient {
    private static final Logger logger = LogManager.getLogger(TCPClient.class);

    public void startClient(String remoteIP, int remotePort) {
        try {
            System.out.println("TCP BIOUDPClient..., Connecting to server " + remoteIP + ": " + remotePort);
            Socket socket = new Socket(remoteIP, remotePort);
            TCPConnection conn = new TCPConnection(socket);
            conn.registerConnListener(new EventHandler("TCP-BIOUDPClient"));
            conn.handleConnect();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.startClient(ENV.SERVER_IP, ENV.BIO_TCP_PORT);
    }
}
