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

    protected static void init(IoAcceptor acceptor) throws IOException {
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new TimeServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(AirIO.MINA_PORT));
    }
}
