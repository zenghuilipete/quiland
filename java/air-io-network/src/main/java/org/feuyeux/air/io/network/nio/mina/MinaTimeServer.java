package org.feuyeux.air.io.network.nio.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.feuyeux.air.io.network.AirIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaTimeServer {
    protected IoAcceptor acceptor;

    protected void init() throws IOException {
        acceptor.getFilterChain().addLast(AirIO.MINA_LOGGER, new LoggingFilter());
        acceptor.getFilterChain().addLast(AirIO.MINA_CODEC,
          new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(AirIO.CHAR_SET))));
        acceptor.getSessionConfig().setReadBufferSize(AirIO.BYTES_SIZE2);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, AirIO.IDLE_TIME);
        acceptor.setHandler(new TimeServerHandler());
        acceptor.bind(new InetSocketAddress(AirIO.MINA_PORT));
    }
}
