package protocolsupport.protocol;

import java.nio.charset.StandardCharsets;

import protocolsupport.protocol.DataStorage.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;

public class InitialPacketDecoder extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object inputObj) throws Exception {
		ByteBuf input = (ByteBuf) inputObj;
		if (input.readableBytes() == 0) {
			return;
		}
		//detect protocol
		ProtocolVersion handshakeversion = ProtocolVersion.UNKNOWN;
		input.markReaderIndex();
		int firstbyte = input.readUnsignedByte();
		if (firstbyte == 0xFE) { //1.6 ping (should we check if FE is actually a part of a varint length?)
			try {
				if (
					input.readUnsignedByte() == 1 &&
					input.readUnsignedByte() == 0xFA &&
					"MC|PingHost".equals(new String(input.readBytes(input.readUnsignedShort() * 2).array(), StandardCharsets.UTF_16BE))
				) {
					input.readUnsignedShort();
					handshakeversion = ProtocolVersion.fromId(input.readUnsignedByte());
					input.resetReaderIndex();
				}
			} catch (IndexOutOfBoundsException ex) {
				input.resetReaderIndex();
				return;
			}
		} else { //1.7 handshake
			input.resetReaderIndex();
			input.markReaderIndex();
			ByteBuf data = getVarIntPrefixedData(input);
			if (data != null) {
				handshakeversion = read1_7_1_8Handshake(data);
			}
			input.resetReaderIndex();
		}
		//if we detected the protocol than we save it and process data
		if (handshakeversion != ProtocolVersion.UNKNOWN) {
			DataStorage.setVersion(channelHandlerContext.channel().remoteAddress(), handshakeversion);
			rebuildPipeLine(channelHandlerContext, input, handshakeversion);
		}
	}

	private void rebuildPipeLine(final ChannelHandlerContext ctx, final ByteBuf input, ProtocolVersion version) throws Exception {
		ctx.channel().pipeline().remove(InitialPacketDecoder.class);
		switch (version) {
			case MINECRAFT_1_8: {
				protocolsupport.protocol.v_1_8.PipeLineBuilder.buildPipeLine(ctx, input);
				break;
			}
			case MINECRAFT_1_7_5: case MINECRAFT_1_7_10: {
				protocolsupport.protocol.v_1_7.PipeLineBuilder.buildPipeLine(ctx, input);
				break;
			}
			case MINECRAFT_1_6_2: case MINECRAFT_1_6_4: {
				protocolsupport.protocol.v_1_6.PipeLineBuilder.buildPipeLine(ctx, input);
				break;
			}
			default: {
				throw new RuntimeException("Not supported yet");
			}
		}
	}

	private ByteBuf getVarIntPrefixedData(final ByteBuf byteBuf) {
        final byte[] array = new byte[3];
        for (int i = 0; i < array.length; ++i) {
            if (!byteBuf.isReadable()) {
                return null;
            }
            array[i] = byteBuf.readByte();
            if (array[i] >= 0) {
                final int length = readVarInt(Unpooled.wrappedBuffer(array));
                if (byteBuf.readableBytes() < length) {
                    return null;
                }
                return byteBuf.readBytes(length);
            }
        }
        throw new CorruptedFrameException("Packet length is wider than 21 bit");
	}

	private ProtocolVersion read1_7_1_8Handshake(ByteBuf data) {
		if (readVarInt(data) == 0x00) {
			return ProtocolVersion.fromId(readVarInt(data));
		}
		return ProtocolVersion.UNKNOWN;
	}

	private int readVarInt(ByteBuf data) {
		int value = 0;
		int length = 0;
		byte b0;
		do {
			b0 = data.readByte();
			value |= (b0 & 0x7F) << length++ * 7;
			if (length > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((b0 & 0x80) == 0x80);
		return value;
	}

}
