package org.feuyeux.air.io.network.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface ENV {
    String SERVER_IP = "127.0.0.1";//"192.168.225.166";
    String BROAD_CAST_IP = "255.255.255.255";
    int BIO_TCP_PORT = 19191;
    int NIO_TCP_PORT = 9119;
    int BIO_UDP_PORT_0 = 9129;
    int BIO_UDP_PORT_1 = 9219;
    int NIO_UDP_PORT_0 = 9139;
    int NIO_UDP_PORT_1 = 9319;
    int MINA_PORT = 9149;
    int NETTY_PORT = 9159;

    Path FROM = Paths.get("C:/1");
    Path TO = Paths.get("C:/1_copy");
    String QUIT = "quit";
    int FIX_THREAD_NUMBER = 5;
    String UTF_8 = "UTF-8";
    int BYTE_SIZE_1K = 1024;
    int BYTE_SIZE_2K = 2048;
    int IDLE_TIME = 10;
    long CONNECT_TIMEOUT = 15 * 1000L; // 15 seconds
    long READ_TIMEOUT = 10;//10 seconds
    String MINA_LOGGER = "logger";
    String MINA_CODEC = "codec";
}