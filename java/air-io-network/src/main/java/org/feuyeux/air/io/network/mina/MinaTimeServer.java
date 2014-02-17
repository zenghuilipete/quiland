package org.feuyeux.air.io.network.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaTimeServer {
    protected final IoAcceptor acceptor;

    public MinaTimeServer(IoAcceptor acceptor) {
        this.acceptor = acceptor;
    }

    protected void init() throws IOException {
        acceptor.getFilterChain().addLast(ENV.MINA_LOGGER, new LoggingFilter());
        acceptor.getFilterChain().addLast(ENV.MINA_CODEC,
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(ENV.UTF_8))));
        acceptor.getSessionConfig().setReadBufferSize(ENV.BYTE_SIZE_2K);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, ENV.IDLE_TIME);
        acceptor.setHandler(new TimeServerHandler());
        acceptor.bind(new InetSocketAddress(ENV.MINA_PORT));
    }
}
