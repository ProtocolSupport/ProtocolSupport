package protocolsupport.zmcpe.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.version.AbstractPacketDecoder;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zmcpe.packetsimpl.serverbound.ClientLogin;
import protocolsupport.zplatform.network.NetworkState;

public class PEPacketDecoder extends AbstractPacketDecoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop.class);
		}
		registry.register(NetworkState.PLAY, 1, ClientLogin.class);
	}

	public PEPacketDecoder(Connection connection, NetworkDataCache cache) {
		super(connection, cache);
	}

	private final ProtocolSupportPacketDataSerializer serializer = new ProtocolSupportPacketDataSerializer(null, connection.getVersion());

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(NetworkState.PLAY, serializer.readUnsignedByte());
		packetTransformer.readFromClientData(serializer);
		if (serializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass().getName() + ", bytes left: " + serializer.readableBytes());
		}
		addPackets(packetTransformer.toNative(), list);
	}

	public static class Noop extends ServerBoundMiddlePacket {

		@Override
		public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
			serializer.skipBytes(serializer.readableBytes());
		}

		@Override
		public RecyclableCollection<ServerBoundPacketData> toNative() {
			return RecyclableEmptyList.get();
		}

	}

}
