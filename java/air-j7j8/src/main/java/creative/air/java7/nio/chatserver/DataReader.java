package creative.air.java7.nio.chatserver;

import java.nio.ByteBuffer;

public interface DataReader {
	void beforeRead(Client client);

	void onData(Client client, ByteBuffer buffer, int bytes);

	boolean acceptsMessages();
}
