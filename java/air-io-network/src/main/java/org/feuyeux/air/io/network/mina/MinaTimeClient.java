package org.feuyeux.air.io.network.mina;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.feuyeux.air.io.network.common.ENV;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaTimeClient {
    protected static final Logger logger = LogManager.getLogger(MinaTimeClient.class);
    protected final IoConnector connector;

    public MinaTimeClient(IoConnector connector) {
        this.connector = connector;
    }

    protected void connect() throws InterruptedException {
        connector.setConnectTimeoutMillis(ENV.CONNECT_TIMEOUT);
        connector.getFilterChain().addLast(ENV.MINA_CODEC,
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(ENV.UTF_8))));
        connector.getFilterChain().addLast(ENV.MINA_LOGGER, new LoggingFilter());
        connector.setHandler(new TimeClientHandler());
        IoSession session;

        for (; ; ) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(ENV.SERVER_IP, ENV.MINA_PORT));
                future.awaitUninterruptibly();
                session = future.getSession();
                break;
            } catch (RuntimeIoException e) {
                logger.error("Failed to connect.");
                logger.error(e);
                Thread.sleep(5000);
            }
        }
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
