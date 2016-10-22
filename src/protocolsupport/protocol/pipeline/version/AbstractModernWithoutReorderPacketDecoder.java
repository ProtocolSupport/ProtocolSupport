package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.netty.ChannelUtils;

public class AbstractModernWithoutReorderPacketDecoder extends AbstractPacketDecoder {

	public AbstractModernWithoutReorderPacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

	private final ProtocolSupportPacketDataSerializer serializer = new ProtocolSupportPacketDataSerializer(null, connection.getVersion());

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get(), serializer.readVarInt());
		packetTransformer.readFromClientData(serializer);
		if (serializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass().getName() + ", bytes left: " + serializer.readableBytes());
		}
		addPackets(packetTransformer.toNative(), list);
	}

}
