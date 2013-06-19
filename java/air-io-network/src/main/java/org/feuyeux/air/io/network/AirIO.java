package org.feuyeux.air.io.network;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface AirIO {
	final String SERVER_IP = "127.0.0.1";//"192.168.225.166";
	final int BIO_TCP_PORT = 9109;
	final int NIO_TCP_PORT = 9119;
	final int BIO_UDP_PORT_0 = 9129;
	final int BIO_UDP_PORT_1 = 9219;
	final int NIO_UDP_PORT_0 = 9139;
	final int NIO_UDP_PORT_1 = 9319;
	final int MINA_PORT = 9149;
	final int COUNT = 2;
	final Path FROM = Paths.get("C:/1");
	final Path TO = Paths.get("C:/1_copy");
	final String QUIT = "quit";
	final int FixThreadNumber = 5;
}