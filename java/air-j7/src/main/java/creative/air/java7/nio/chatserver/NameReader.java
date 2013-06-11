package creative.air.java7.nio.chatserver;

import java.nio.ByteBuffer;

/**
 * The first state a newly connected {@code Client} is in, this handles writing out the welcoming message and reads the response up to a newline. When a newline
 * character have been received it changes the handler from NameReader to MessageReader on the client.
 */
class NameReader implements DataReader {
	private final StringBuilder buffer = new StringBuilder();
	private final ChatServer chatServer;
	private boolean once = true;
	private static final String NEWLINE = "\n";

	public NameReader(ChatServer chatServer) {
		this.chatServer = chatServer;
	}

	/**
	 * Writes the welcoming message to the client the first time this method is called.
	 * 
	 * @param client
	 *            the client to receive the message
	 */
	@Override
	public void beforeRead(Client client) {
		// if it is a long name that takes more than one read we only want to display Name: once.
		if (once) {
			client.writeStringMessage("Name: ");
			once = false;
		}
	}

	public boolean acceptsMessages() {
		return false;
	}

	/**
	 * Receives incoming data from the socket, searches for a newline and tries to set the username if one is found
	 */
	@Override
	public void onData(Client client, ByteBuffer buffer, int bytes) {
		buffer.flip();
		String name;
		name = this.buffer.append(new String(buffer.array(), 0, bytes)).toString();
		if (name.contains(NEWLINE)) {
			onUserNameRead(client, name);
		}
	}

	/**
	 * Splits the name on the newlines, takes the first as the username and appends everything else to the clients message buffer. Sets the clients handler to
	 * MessageReader.
	 * 
	 * @param client
	 *            the client to set the username for
	 * @param name
	 *            the string containing the buffered input
	 */
	private void onUserNameRead(Client client, String name) {
		String[] strings = name.split(NEWLINE, 2);
		client.setUserName(strings[0].trim());
		sendRemainingParts(client, strings);
		client.setReader(new ClientReader(chatServer, new MessageReader(chatServer)));
		client.writeStringMessage("Welcome " + client.getUserName() + "\n");
	}

	/**
	 * Appends the remaining parts to the clients message buffer
	 * 
	 * @param client
	 *            the client
	 * @param strings
	 *            the messages to append to the buffer
	 */
	private void sendRemainingParts(Client client, String[] strings) {
		for (int i = 1; i < strings.length; ++i) {
			client.appendMessage(strings[i]);
		}
	}
}
