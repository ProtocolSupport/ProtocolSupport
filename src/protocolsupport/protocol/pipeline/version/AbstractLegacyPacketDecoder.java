package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.Connection;
import protocolsupport.protocol.legacyremapper.LegacyAnimatePacketReorderer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.ReplayingProtocolSupportSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.types.NetworkListenerState;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;

public class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	private final ReplayingProtocolSupportSupportPacketDataSerializer serializer = new ReplayingProtocolSupportSupportPacketDataSerializer(connection.getVersion());
	private final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		serializer.markReaderIndex();
		try {
			ServerBoundMiddlePacket packetTransformer = registry.getTransformer(NetworkListenerState.getFromChannel(ctx.channel()), serializer.readUnsignedByte());
			packetTransformer.readFromClientData(serializer);
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
		}
	}

}
