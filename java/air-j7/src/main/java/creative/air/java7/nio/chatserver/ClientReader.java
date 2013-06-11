package creative.air.java7.nio.chatserver;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

/**
 * Handles a cycle of reading / writing on the {@code Client}.
 */
class ClientReader {
	private final DataReader callback;
	private final ChatServer chatServer;

	ClientReader(ChatServer chatServer, DataReader callback) {
		this.chatServer = chatServer;
		this.callback = callback;
	}

	public boolean acceptsMessages() {
		return callback.acceptsMessages();
	}

	/**
	 * Runs a cycle of doing a beforeRead action and then enqueing a new read on the client. Handles closed channels and errors while reading. If the client is
	 * still connected a new round of actions are called.
	 */
	public void run(final Client client) {
		callback.beforeRead(client);
		client.read(new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				// if result is negative or zero the connection has been closed or something gone wrong
				if (result < 1) {
					client.close();
					System.out.println("Closing connection to " + client);
					chatServer.removeClient(client);
				} else {
					callback.onData(client, buffer, result);
					// enqueue next round of actions
					client.run();
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer buffer) {
				client.close();
				chatServer.removeClient(client);
			}
		});
	}
}
