package org.feuyeux.air.io.network.nio.mina;

import org.apache.logging.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.feuyeux.air.io.network.AirIO;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaTimeClient {
    protected static Logger logger;
    protected IoConnector connector;

    protected void connect() throws InterruptedException {
        connector.setConnectTimeoutMillis(AirIO.CONNECT_TIMEOUT);
        connector.getFilterChain().addLast(AirIO.MINA_CODEC,
          new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(AirIO.CHAR_SET))));
        connector.getFilterChain().addLast(AirIO.MINA_LOGGER, new LoggingFilter());
        connector.setHandler(new TimeClientHandler());
        IoSession session;

        for (; ; ) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(AirIO.SERVER_IP, AirIO.MINA_PORT));
                future.awaitUninterruptibly();
                session = future.getSession();
                break;
            } catch (RuntimeIoException e) {
                logger.error("Failed to connect.");
                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
