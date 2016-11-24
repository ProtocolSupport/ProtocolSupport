package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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

	private final ByteBuf cumulation = Unpooled.buffer();
	private final ReplayingProtocolSupportSupportPacketDataSerializer serializer = new ReplayingProtocolSupportSupportPacketDataSerializer(connection.getVersion());
	{
		serializer.setBuf(cumulation);
	}
	private final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	private ServerBoundMiddlePacket packetTransformer = null;

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		cumulation.writeBytes(input);
		if (packetTransformer == null) {
			packetTransformer = registry.getTransformer(NetworkListenerState.getFromChannel(ctx.channel()), serializer.readUnsignedByte());
		}
		serializer.markReaderIndex();
		try {
			packetTransformer.readFromClientData(serializer);
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
			packetTransformer = null;
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
		}
	}

}
