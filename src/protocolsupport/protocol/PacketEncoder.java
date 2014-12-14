package protocolsupport.protocol;

import protocolsupport.protocol.DataStorage.ProtocolVersion;
import net.minecraft.server.v1_8_R1.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
		ProtocolVersion version = DataStorage.getVersion(channelHandlerContext.channel().remoteAddress());
		switch (version) {
			case MINECRAFT_1_8: {
				protocolsupport.protocol.v_1_8.FullPacketEncoder.encodePacket(channelHandlerContext.channel(), packet, byteBuf);
				return;
			}
			case MINECRAFT_1_7_5: case MINECRAFT_1_7_10: {
				protocolsupport.protocol.v_1_7.clientboundtransformer.FullPacketEncoder.encodePacket(channelHandlerContext.channel(), packet, byteBuf);
				return;
			}
			case MINECRAFT_1_6_2: case MINECRAFT_1_6_4: {
				protocolsupport.protocol.v_1_6.clientboundtransformer.FullPacketEncoder.encodePacket(channelHandlerContext.channel(), packet, byteBuf);
			}
			case UNKNOWN: {
				throw new CorruptedFrameException("Unknown protocol version");
			}
		}
	}

}
