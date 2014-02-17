package org.feuyeux.air.io.network.mina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.feuyeux.air.io.network.common.ENV;

public class TimeClientHandler extends IoHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(TimeClientHandler.class);
    public static final String TELL_ME_THE_TIME = "tell me the time.";
    private static int times = 5;

    @Override
    public void sessionOpened(IoSession session) {
        session.write(TELL_ME_THE_TIME);
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        if (message == null || message.toString().isEmpty()) {
            session.close(true);
            return;
        }
        logger.debug("BIOUDPServer response:{}", message);
        if (times-- > 0) {
            session.write(TELL_ME_THE_TIME);
        } else {
            session.write(ENV.QUIT);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
    }
}
