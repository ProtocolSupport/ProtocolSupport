package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.Connection;
import protocolsupport.protocol.legacyremapper.LegacyAnimatePacketReorderer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.ReplayingProtocolSupportSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.network.NetworkListenerState;

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

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		cumulation.writeBytes(input);
		while (serializer.isReadable()) {
			if (!decode0(ctx.channel(), list)) {
				break;
			}
		}
		cumulation.discardSomeReadBytes();
	}

	private boolean decode0(Channel channel, List<Object> list) throws InstantiationException, IllegalAccessException {
		serializer.markReaderIndex();
		try {
			ServerBoundMiddlePacket packetTransformer = registry.getTransformer(NetworkListenerState.getFromChannel(channel), serializer.readUnsignedByte());
			packetTransformer.readFromClientData(serializer);
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
			packetTransformer = null;
			return true;
		} catch (EOFSignal ex) {
			serializer.resetReaderIndex();
			return false;
		}
	}

}
