package org.feuyeux.air.io.network.bio.tcp.callback;

import org.feuyeux.air.io.network.common.ENV;

import java.net.Socket;

public class TCPClient {
    public void startClient(String remoteIP, int remotePort) {
        try {
            System.out.println("TCP BIOUDPClient..., Connecting to server " + remoteIP + ": " + remotePort);
            Socket socket = new Socket(remoteIP, remotePort);
            TCPConnection conn = new TCPConnection(socket);
            conn.registerConnListener(new EventHandler("TCP-BIOUDPClient"));
            conn.handleConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.startClient(ENV.SERVER_IP, ENV.BIO_TCP_PORT);
    }
}
