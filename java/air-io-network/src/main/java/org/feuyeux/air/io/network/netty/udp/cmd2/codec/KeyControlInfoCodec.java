package org.feuyeux.air.io.network.netty.udp.cmd2.codec;

import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;

/**
 * Created by erichan on 2/10/14.
 */
public class KeyControlInfoCodec implements ControlInfoCodec {
    KeyControlInfoCodec() {
    }

    @Override
    public byte[] encode(ControlInfo controlInfo) {
        KeyControlInfo keyControlInfo = (KeyControlInfo) controlInfo;
        return keyControlInfo.getKeyPress().getBytes();
    }

    @Override
    public ControlInfo decode(byte[] buf) {
        KeyControlInfo keyControlInfo = new KeyControlInfo();
        keyControlInfo.setKeyPress(new String(buf));
        return keyControlInfo;
    }
}
