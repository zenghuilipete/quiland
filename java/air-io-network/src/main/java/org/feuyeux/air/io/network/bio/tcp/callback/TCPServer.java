package org.feuyeux.air.io.network.bio.tcp.callback;

import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.startServer(ENV.BIO_TCP_PORT);
    }
}
