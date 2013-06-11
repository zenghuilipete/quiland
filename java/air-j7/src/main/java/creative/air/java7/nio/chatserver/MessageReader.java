package creative.air.java7.nio.chatserver;

import java.nio.ByteBuffer;

/**
 * Writes all messages in our buffer to the other clients and appends new data read from the socket to our buffer
 */
class MessageReader implements DataReader {
	private final ChatServer chatServer;

	public MessageReader(ChatServer chatServer) {
		this.chatServer = chatServer;
	}

	public boolean acceptsMessages() {
		return true;
	}

	/**
	 * Write all full messages in our buffer to the other clients
	 * 
	 * @param client
	 *            the client to read messages from
	 */
	@Override
	public void beforeRead(Client client) {
		// Check if we have any messages buffered and send them
		String message = client.nextMessage();
		while (message != null) {
			chatServer.writeMessageToClients(client, message);
			message = client.nextMessage();
		}
	}

	/**
	 * Append the read buffer to the clients message buffer
	 * 
	 * @param client
	 *            the client to append messages to
	 * @param buffer
	 *            the buffer we received from the socket
	 * @param bytes
	 *            the number of bytes read into the buffer
	 */
	@Override
	public void onData(Client client, ByteBuffer buffer, int bytes) {
		buffer.flip();
		// Just append the message on the buffer
		client.appendMessage(new String(buffer.array(), 0, bytes));
	}
}
