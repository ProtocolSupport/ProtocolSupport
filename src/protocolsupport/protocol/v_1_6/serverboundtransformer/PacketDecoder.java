package protocolsupport.protocol.v_1_6.serverboundtransformer;

import java.util.Arrays;
import java.util.List;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class PacketDecoder extends ByteToMessageDecoder {

	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	public static Packet[] decodePacket(Channel channel, final ByteBuf bytebuf) throws Exception {

		return null;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> packets) throws Exception {
		Channel channel = ctx.channel();
		input.markReaderIndex();
		try {
			EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
			int packetId = input.readUnsignedByte();
			packets.addAll(
				Arrays.asList(
					transformers[currentProtocol.ordinal()].tranform(
						channel,
						packetId,
						new PacketDataSerializer(input, DataStorage.getVersion(channel.remoteAddress()))
					)
				)
			);
		} catch (IndexOutOfBoundsException ex) {
		}
		input.resetReaderIndex();
	}

}
