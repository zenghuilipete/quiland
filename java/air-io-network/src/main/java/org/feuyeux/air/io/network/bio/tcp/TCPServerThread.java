package org.feuyeux.air.io.network.bio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPServerThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(TCPServerThread.class);

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    TCPServerThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    Thread.sleep(100);
                    continue;
                }
                if (ENV.QUIT.equalsIgnoreCase(line.trim())) {
                    in.close();
                    out.close();
                    logger.info("Socket BIOUDPServer Thread has been shutdown!");
                    Thread.sleep(100);
                    System.exit(0);
                } else {
                    logger.info("Message from client: " + line);
                    out.println("BIOUDPServer response： " + line);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {
                System.err.println("socket can not closed.");
            }
        }
    }
}
