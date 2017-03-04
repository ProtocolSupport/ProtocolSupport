package protocolsupport.zmcpe.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.version.AbstractPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;
import protocolsupport.zmcpe.packetsimpl.serverbound.ClientLogin;
import protocolsupport.zmcpe.packetsimpl.serverbound.PlayerAction;
import protocolsupport.zmcpe.packetsimpl.serverbound.PositionLook;
import protocolsupport.zplatform.network.NetworkState;

public class PEPacketDecoder extends AbstractPacketDecoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop.class);
		}
		registry.register(NetworkState.PLAY, PEPacketIDs.LOGIN, ClientLogin.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.PLAYER_MOVE, PositionLook.class);
		registry.register(NetworkState.PLAY, PEPacketIDs.PLAYER_ACTION, PlayerAction.class);
	}

	public PEPacketDecoder(Connection connection, NetworkDataCache cache) {
		super(connection, cache);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(NetworkState.PLAY, input.readUnsignedByte());
		packetTransformer.readFromClientData(input, connection.getVersion());
		if (input.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass().getName() + ", bytes left: " + input.readableBytes());
		}
		addPackets(packetTransformer.toNative(), list);
	}

	public static class Noop extends ServerBoundMiddlePacket {

		@Override
		public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
			clientdata.skipBytes(clientdata.readableBytes());
		}

		@Override
		public RecyclableCollection<ServerBoundPacketData> toNative() {
			return RecyclableEmptyList.get();
		}

	}

}
