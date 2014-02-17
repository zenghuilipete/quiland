package org.feuyeux.air.io.network.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.bio.tcp.callback.TCPConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemInReadThread extends Thread {
    private static final Logger logger = LogManager.getLogger(SystemInReadThread.class);
    private final TCPConnection conn;

    public SystemInReadThread(TCPConnection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        System.out.println("Please input your message that will be sent.");

        String msg;
        try {
            BufferedReader sysReader = new BufferedReader(new InputStreamReader(System.in));
            while ((msg = sysReader.readLine()) != null) {
                if (conn != null) {
                    conn.sendMessage(msg);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
