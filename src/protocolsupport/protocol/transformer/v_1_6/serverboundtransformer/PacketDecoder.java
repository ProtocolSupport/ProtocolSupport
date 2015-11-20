package protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketDecoder;

import protocolsupport.utils.ReplayingDecoderBuffer;
import protocolsupport.utils.ReplayingDecoderBuffer.EOFSignal;

public class PacketDecoder implements IPacketDecoder {

	private ProtocolVersion version;
	public PacketDecoder(ProtocolVersion version) {
		this.version = version;
	}

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.setCumulation(input);
		buffer.markReaderIndex();
		Channel channel = ctx.channel();
		try {
			int packetId = buffer.readUnsignedByte();
			Collection<Packet<?>> transformedPackets = transformers[channel.attr(currentStateAttrKey).get().ordinal()].tranform(
				channel, packetId, new PacketDataSerializer(buffer, version)
			);
			if (transformedPackets != null) {
				list.addAll(transformedPackets);
			} else {
				throw new IOException("Can't deserialize unknown packet "+packetId);
			}
		} catch (EOFSignal ex) {
			buffer.resetReaderIndex();
		}
	}

}
