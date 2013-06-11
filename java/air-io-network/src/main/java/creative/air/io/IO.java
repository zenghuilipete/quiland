package creative.air.io;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface IO {
	String SERVER_IP = "127.0.0.1";//"192.168.225.166";
	int BIO_TCP_PORT = 9109;
	int NIO_TCP_PORT = 9119;
	int BIO_UDP_PORT_0 = 9129;
	int BIO_UDP_PORT_1 = 9219;
	int NIO_UDP_PORT_0 = 9139;
	int NIO_UDP_PORT_1 = 9319;
	int MINA_PORT = 9149;
	int COUNT = 2;
	Path FROM = Paths.get("C:/1");
	Path TO = Paths.get("C:/1_copy");
}