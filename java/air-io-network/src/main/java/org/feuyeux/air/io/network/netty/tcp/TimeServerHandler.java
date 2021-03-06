package org.feuyeux.air.io.network.netty.tcp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(TimeServerHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        String str = message.toString();
        logger.debug("BIOUDPClient message:{}", str);
        if (str.trim().equalsIgnoreCase(ENV.QUIT)) {
            ctx.close();
            return;
        }
        Date date = new Date();
        ctx.write(date.toString());
        logger.debug("Message written...");
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
