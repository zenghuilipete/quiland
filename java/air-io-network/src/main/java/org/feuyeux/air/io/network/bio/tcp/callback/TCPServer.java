package org.feuyeux.air.io.network.bio.tcp.callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final Logger logger = LogManager.getLogger(TCPServer.class);

    public void startServer(int localPort) {
        try {
            ServerSocket serverSocket = new ServerSocket(localPort);
            System.out.println("TCP BIOUDPServer... on port " + localPort);
            while (true) {
                Socket socket = serverSocket.accept();
                TCPConnection conn = new TCPConnection(socket);
                conn.registerConnListener(new EventHandler("TCP-BIOUDPServer"));
                conn.handleConnect();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.startServer(ENV.BIO_TCP_PORT);
    }
}
