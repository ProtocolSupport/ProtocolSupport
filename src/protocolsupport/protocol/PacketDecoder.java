package protocolsupport.protocol;

import java.nio.charset.StandardCharsets;
import java.util.List;

import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf input, final List<Object> list) throws Exception {
		if (input.readableBytes() == 0) {
			return;
		}
		ProtocolVersion version = DataStorage.getVersion(channelHandlerContext.channel().remoteAddress());
		if (version != ProtocolVersion.UNKNOWN) {
			decodeWithKnownVersion(channelHandlerContext, input, list, version);
		} else {
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
				ByteBuf data = getVarIntPrefixedData(input, false);
				if (data != null) {
					handshakeversion = read1_7_1_8Handshake(data);
				}
				input.resetReaderIndex();
			}
			//if we detected the protocol than we save it and process data
			if (handshakeversion != ProtocolVersion.UNKNOWN) {
				DataStorage.setVersion(channelHandlerContext.channel().remoteAddress(), handshakeversion);
				decodeWithKnownVersion(channelHandlerContext, input, list, handshakeversion);
			}
		}
	}

	private void decodeWithKnownVersion(final ChannelHandlerContext channelHandlerContext, final ByteBuf input, final List<Object> list, ProtocolVersion version) throws Exception {
		switch (version) {
			case MINECRAFT_1_8: {
				ByteBuf data = getVarIntPrefixedData(input, true);
				if (data != null && data.readableBytes() != 0) {
					channelHandlerContext.fireChannelRead(protocolsupport.protocol.v_1_8.FullPacketDecoder.decodePacket(channelHandlerContext.channel(), data));
				}
				break;
			}
			case MINECRAFT_1_7_5: case MINECRAFT_1_7_10: {
				ByteBuf data = getVarIntPrefixedData(input, true);
				if (data != null && data.readableBytes() != 0) {
					channelHandlerContext.fireChannelRead(protocolsupport.protocol.v_1_7.serverboundtransformer.FullPacketDecoder.decodePacket(channelHandlerContext.channel(), data));
				}
				break;
			}
			case MINECRAFT_1_6_2: case MINECRAFT_1_6_4: {
				if (input.readableBytes() != 0) {
					Packet[] packets = protocolsupport.protocol.v_1_6.serverboundtransformer.FullPacketDecoder.decodePacket(channelHandlerContext.channel(), input);
					if (packets != null) {
						for (Packet packet : packets) {
							channelHandlerContext.fireChannelRead(packet);
						}
					}
				}
				break;
			}
			default: {
				throw new RuntimeException("Not supported yet");
			}
		}
	}

	private ByteBuf getVarIntPrefixedData(final ByteBuf byteBuf, boolean resetIndexIfFailure) {
		if (resetIndexIfFailure) {
			byteBuf.markReaderIndex();
		}
        final byte[] array = new byte[3];
        for (int i = 0; i < array.length; ++i) {
            if (!byteBuf.isReadable()) {
        		if (resetIndexIfFailure) {
        			byteBuf.resetReaderIndex();
        		}
                return null;
            }
            array[i] = byteBuf.readByte();
            if (array[i] >= 0) {
                final int length = readVarInt(Unpooled.wrappedBuffer(array));
                if (byteBuf.readableBytes() < length) {
            		if (resetIndexIfFailure) {
            			byteBuf.resetReaderIndex();
            		}
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
