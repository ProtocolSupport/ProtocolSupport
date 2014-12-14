package protocolsupport.protocol.v_1_6.serverboundtransformer;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class FullPacketDecoder {

	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	public static Packet[] decodePacket(Channel channel, final ByteBuf bytebuf) throws Exception {
		bytebuf.markReaderIndex();
		try {
			EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
			int packetId = bytebuf.readUnsignedByte();
			return transformers[currentProtocol.ordinal()].tranform(channel, packetId, new PacketDataSerializer(bytebuf, DataStorage.getVersion(channel.remoteAddress())));
		} catch (IndexOutOfBoundsException ex) {
		}
		bytebuf.resetReaderIndex();
		return null;
	}

}
