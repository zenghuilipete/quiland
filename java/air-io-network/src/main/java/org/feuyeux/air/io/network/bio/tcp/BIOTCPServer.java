package org.feuyeux.air.io.network.bio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOTCPServer {
    private static final Logger logger = LogManager.getLogger(BIOTCPServer.class);

    final ServerSocket serverSocket;
    final int port;
    private final ExecutorService e;

    public BIOTCPServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(this.port);
        e = Executors.newFixedThreadPool(ENV.FIX_THREAD_NUMBER);
        initialize();
    }

    private void initialize() throws IOException {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("the remote port connected: " + socket.getPort());
                try {
                    Runnable r = new TCPServerThread(socket);
                    e.execute(r);
                } catch (Exception ignored) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        new BIOTCPServer(ENV.BIO_TCP_PORT);
    }
}
