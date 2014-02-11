package org.feuyeux.air.io.network.netty.tcp;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

public class TimeClientHandler extends ChannelHandlerAdapter {
    public static final String TELL_ME_THE_TIME = "tell me the time.";
    private final static Logger logger = LogManager.getLogger(TimeClientHandler.class);
    private static int TIMES = 5;

    public TimeClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(TELL_ME_THE_TIME);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        if (message == null || message.toString().isEmpty()) {
            ctx.close();
            return;
        }
        logger.debug("BIOUDPServer response:{}", message);
        if (TIMES-- > 0) {
            ctx.write(TELL_ME_THE_TIME);
        } else {
            ctx.write(ENV.QUIT);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
