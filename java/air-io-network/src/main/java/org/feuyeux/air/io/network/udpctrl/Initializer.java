package org.feuyeux.air.io.network.udpctrl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.feuyeux.air.io.network.udpctrl.udphandler.ClientHandler;

/**
 * Created by erichan on 2/10/14.
 */
public class Initializer extends ChannelInitializer<NioDatagramChannel> {

    public Initializer() {

    }

    @Override
    protected void initChannel(NioDatagramChannel chan) throws Exception {
        ChannelPipeline pipe = chan.pipeline();
        //pipe.addLast("encoder", new ControlEventEncoder());
        pipe.addLast("handler", new ClientHandler());
    }
}