package org.feuyeux.air.io.network.netty.udp.cmd;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by Administrator on 14-2-10.
 */
public class UDPCommandCodec {
    public static ByteBuf encode(UDPCommand udpCommand) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(udpCommand.getType());
        byteBuf.writeBytes(udpCommand.getPair().getBytes());
        return byteBuf;
    }

    public static UDPCommand decode(ByteBuf byteBuf) throws Exception {
        int type = byteBuf.readInt();
        int currentIndex = byteBuf.readerIndex();
        int endIndex = byteBuf.writerIndex();

        byte[] dst = new byte[endIndex - currentIndex];
        byteBuf.readBytes(dst);
        String pair = new String(dst);
        UDPCommand udpCommand = new UDPCommand(type, pair);
        return udpCommand;
    }
}
