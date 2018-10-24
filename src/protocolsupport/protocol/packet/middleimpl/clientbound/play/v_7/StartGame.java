package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class StartGame extends MiddleStartGame {

	public StartGame(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_LOGIN_ID);
		serializer.writeInt(playerEntityId);
		serializer.writeByte(gamemode.getId() | (hardcore ? 0x8 : 0));
		serializer.writeByte(dimension.getId());
		serializer.writeByte(difficulty.getId());
		serializer.writeByte(Math.min(maxplayers, 60));
		StringSerializer.writeString(serializer, version, leveltype);
		return RecyclableSingletonList.create(serializer);
	}

}
