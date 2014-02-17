package org.feuyeux.air.io.network.netty.tcp;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

public class TimeClientHandler extends ChannelHandlerAdapter {
    public static final String TELL_ME_THE_TIME = "tell me the time.";
    private static final Logger logger = LogManager.getLogger(TimeClientHandler.class);
    private static int times = 5;

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
        if (times-- > 0) {
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
