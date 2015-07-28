package com.github.shevchik.protocolsupport.protocol.transformer.v_1_8;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;
import com.github.shevchik.protocolsupport.protocol.core.IPacketSplitter;

public class PacketSplitter implements IPacketSplitter {

	@Override
	public void split(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		input.markReaderIndex();
        final byte[] array = new byte[3];
        for (int i = 0; i < array.length; ++i) {
            if (!input.isReadable()) {
            	input.resetReaderIndex();
                return;
            }
            array[i] = input.readByte();
            if (array[i] >= 0) {
                final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(array), ProtocolVersion.MINECRAFT_1_8);
                try {
                    final int length = packetDataSerializer.readVarInt();
                    if (input.readableBytes() < length) {
                    	input.resetReaderIndex();
                        return;
                    }
                    list.add(input.readBytes(length));
                    return;
                } finally {
                    packetDataSerializer.release();
                }
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
	}

}
