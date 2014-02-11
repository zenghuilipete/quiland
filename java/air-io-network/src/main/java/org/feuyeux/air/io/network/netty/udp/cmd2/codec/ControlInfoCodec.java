package org.feuyeux.air.io.network.netty.udp.cmd2.codec;

import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;

/**
 * Created by erichan on 2/10/14.
 */
public interface ControlInfoCodec {

    public byte[] encode(ControlInfo controlInfo);

    public ControlInfo decode(byte[] buf);
}
