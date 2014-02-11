package org.feuyeux.air.io.network.mina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.feuyeux.air.io.network.common.ENV;

import java.util.Date;

public class TimeServerHandler extends IoHandlerAdapter {
    private final static Logger logger = LogManager.getLogger(TimeServerHandler.class);

    @Override
    public void sessionCreated(IoSession session) throws Exception {

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        logger.debug("BIOUDPClient message:{}", str);
        if (str.trim().equalsIgnoreCase(ENV.QUIT)) {
            session.close(true);
            return;
        }
        Date date = new Date();
        session.write(date.toString());
        logger.debug("Message written...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.debug("IDLE " + session.getIdleCount(status));
    }
}