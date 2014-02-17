package org.feuyeux.air.io.network.bio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOTCPClient {
    private static final Logger logger = LogManager.getLogger(BIOTCPClient.class);
    final Socket socket;
    final String host;
    final int port;
    final BufferedReader reader;
    final PrintWriter writer;

    public BIOTCPClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(this.host, this.port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String sendMessage(String message) throws IOException {
        if (message.isEmpty()) {
            return "";
        }
        String result = null;
        try {
            writer.println(message);
            writer.flush();
            return result = reader.readLine();
        } catch (Exception e) {
            logger.error(e);
            return "";
        } finally {
            if (result == null) {
                close();
            }
        }
    }

    public void readIn() throws IOException {
        boolean flag = true;
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (flag) {
            String command = systemIn.readLine();
            if (command == null || ENV.QUIT.equalsIgnoreCase(command.trim())) {
                flag = false;
                close();
                continue;
            }
            String result = sendMessage(command);
            if (!result.isEmpty()) {
                logger.info(result);
            }
        }
    }

    public void close() throws IOException {
        logger.info("BIOUDPClient quit!");
        writer.println(ENV.QUIT);
        writer.close();
        reader.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        BIOTCPClient client = new BIOTCPClient(ENV.SERVER_IP, ENV.BIO_TCP_PORT);
        client.readIn();
    }
}
