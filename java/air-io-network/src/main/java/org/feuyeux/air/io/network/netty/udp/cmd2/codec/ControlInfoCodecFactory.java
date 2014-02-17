package org.feuyeux.air.io.network.netty.udp.cmd2.codec;

import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;

/**
 * Created by erichan on 2/10/14.
 */
public final class ControlInfoCodecFactory {
    private ControlInfoCodecFactory() {
    }

    public static ControlInfoCodec getCodec(int type) {
        CommandType commandType = CommandType.getInstance(type);
        return getCodec(commandType);
    }

    public static ControlInfoCodec getCodec(CommandType commandType) {
        switch (commandType) {
            case KEY: {
                return new KeyControlInfoCodec();
            }
            default: {
                return null;
            }
        }
    }
}
