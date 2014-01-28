package org.feuyeux.air.io.network.nio.mina.udp;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.feuyeux.air.io.network.AirIO;
import org.feuyeux.air.io.network.nio.mina.MinaTimeServer;
import org.feuyeux.air.io.network.nio.mina.TimeServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaUDPTimeServer extends MinaTimeServer {
    public static void main(String[] args) throws IOException {
        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
        init(acceptor);
        acceptor.getSessionConfig().setReuseAddress(true);
    }
}