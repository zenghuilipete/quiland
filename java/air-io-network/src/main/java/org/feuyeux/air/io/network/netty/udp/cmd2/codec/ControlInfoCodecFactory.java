package org.feuyeux.air.io.network.netty.udp.cmd2.codec;

import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;

/**
 * Created by erichan on 2/10/14.
 */
public class ControlInfoCodecFactory {
    public static ControlInfoCodec getInstance(int type) {
        CommandType commandType = CommandType.getInstance(type);
        return getInstance(commandType);
    }

    public static ControlInfoCodec getInstance(CommandType commandType) {
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
