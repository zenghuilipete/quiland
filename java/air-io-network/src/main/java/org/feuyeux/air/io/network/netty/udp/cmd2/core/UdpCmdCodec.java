package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.feuyeux.air.io.network.netty.udp.cmd2.codec.ControlInfoCodecFactory;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;
import org.feuyeux.air.io.network.netty.udp.cmd2.entity.UdpCommand;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;

/**
 * Created by Administrator on 14-2-10.
 */
public class UdpCmdCodec {
    public static ByteBuf encode(UdpCommand udpCommand) {
        ByteBuf byteBuf = Unpooled.buffer();
        CommandType commandType = udpCommand.getType();
        byteBuf.writeInt(commandType.ordinal());
        byteBuf.writeBytes(ControlInfoCodecFactory.getInstance(commandType).encode(udpCommand.getControlInfo()));
        return byteBuf;
    }

    public static UdpCommand decode(ByteBuf byteBuf) {
        int type = byteBuf.readInt();
        int currentIndex = byteBuf.readerIndex();
        int endIndex = byteBuf.writerIndex();

        byte[] dst = new byte[endIndex - currentIndex];
        byteBuf.readBytes(dst);
        ControlInfo controlInfo = ControlInfoCodecFactory.getInstance(type).decode(dst);
        UdpCommand udpCommand = new UdpCommand(CommandType.getInstance(type), controlInfo);
        return udpCommand;
    }
}
