package protocolsupport.zmcpe.pipeline;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.version.AbstractPacketEncoder;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zmcpe.packetsimpl.clientbound.Chat;
import protocolsupport.zmcpe.packetsimpl.clientbound.Chunk;
import protocolsupport.zmcpe.packetsimpl.clientbound.CustomPayload;
import protocolsupport.zmcpe.packetsimpl.clientbound.KickDisconnect;
import protocolsupport.zmcpe.packetsimpl.clientbound.Login;
import protocolsupport.zmcpe.packetsimpl.clientbound.LoginSuccess;
import protocolsupport.zmcpe.packetsimpl.clientbound.Position;
import protocolsupport.zmcpe.packetsimpl.clientbound.SetHealth;
import protocolsupport.zplatform.network.NetworkState;

public class PEPacketEncoder extends AbstractPacketEncoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop.class);
		}
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, Position.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
	}

	public PEPacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return oldPacketId;
	}

	public static class Noop extends ClientBoundMiddlePacket<RecyclableCollection<ClientBoundPacketData>> {

		@Override
		public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
			serializer.skipBytes(serializer.readableBytes());
		}

		@Override
		public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
			return RecyclableEmptyList.get();
		}

	}

}
