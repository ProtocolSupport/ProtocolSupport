package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.unsafe.Connection;
import protocolsupport.protocol.legacyremapper.LegacyAnimatePacketReorderer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.ReplayingProtocolSupportSupportPacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;

public class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(Connection connection, SharedStorage storage) {
		super(connection, storage);
	}

	private final ReplayingProtocolSupportSupportPacketDataSerializer serializer = new ReplayingProtocolSupportSupportPacketDataSerializer(connection.getVersion());
	private final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		serializer.markReaderIndex();
		try {
			ServerBoundMiddlePacket packetTransformer = registry.getTransformer(ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get(), serializer.readUnsignedByte());
			packetTransformer.readFromClientData(serializer);
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
		}
	}

}
