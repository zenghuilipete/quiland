package creative.air.io.mina;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;

import creative.air.io.IO;

public class Server {

	public static void main(String[] args) throws Exception {
		final IoAcceptor acceptor = new SocketAcceptor(Runtime.getRuntime().availableProcessors() + 1, Executors.newCachedThreadPool());
		acceptor.getFilterChain().addLast("stringserialize", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		IoHandler handler = new IoHandlerAdapter() {

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				if ("quit".equalsIgnoreCase(message.toString())) {
					acceptor.unbindAll();
					System.out.println("Server has been shutdown!");
					System.exit(0);
				}
				System.out.println("Message from client: " + message);
				session.write("Server response：" + message);
			}

		};
		acceptor.bind(new InetSocketAddress(IO.MINA_PORT), handler);
		System.out.println("Server listen on port: " + IO.MINA_PORT);
	}

}
