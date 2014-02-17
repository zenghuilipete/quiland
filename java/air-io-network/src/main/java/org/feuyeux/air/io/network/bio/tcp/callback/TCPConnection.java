package org.feuyeux.air.io.network.bio.tcp.callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TCPConnection {
    private static final Logger logger = LogManager.getLogger(TCPConnection.class);
    protected final Socket socket;

    protected final BlockingQueue<EventHandler> messageListeners = new ArrayBlockingQueue<>(10);
    protected final BlockingQueue<EventHandler> connectionListeners = new ArrayBlockingQueue<>(10);

    public TCPConnection(Socket socket) {
        this.socket = socket;
    }

    public void registerMsg(EventHandler listener) {
        messageListeners.add(listener);
    }

    public void unregisterMsg(EventHandler listener) {
        messageListeners.remove(listener);
    }

    public void handleReceivedMessage(String msg) {
        for (EventHandler listener : messageListeners) {
            listener.onMessageReceived(this, msg);
        }
    }

    public void registerConnListener(EventHandler listener) {
        connectionListeners.add(listener);
    }

    public void unregisterConnListener(EventHandler listener) {
        connectionListeners.remove(listener);
    }

    public void handleConnect() {
        for (EventHandler listener : connectionListeners) {
            listener.onConnected(this);
        }
    }

    public void receiveMessage() {
        try {
/*
BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = bReader.readLine()) != null) {
				handleReceivedMessage(line);
			}
			*/
            byte[] bytes = new byte[ENV.BYTE_SIZE_1K];
            String msg;
            int length;
            while ((length = socket.getInputStream().read(bytes)) != -1) {
                msg = new String(bytes, 0, length, ENV.UTF_8);
                handleReceivedMessage(msg);
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        socket.getOutputStream().write(msg.getBytes(ENV.UTF_8));
        socket.getOutputStream().flush();
        System.out.println("-->:" + msg);
    }

    public void close() throws IOException {
        socket.close();
    }

    @Override
    public String toString() {
        return "[TCPConnection: local address/port: " + socket.getLocalAddress().getHostAddress() + "/" + socket.getLocalPort() + "; remote address/port: "
                + socket.getInetAddress() + "/" + socket.getPort() + "]";
    }
}
