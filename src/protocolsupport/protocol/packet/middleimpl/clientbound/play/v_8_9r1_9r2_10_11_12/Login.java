package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLogin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Login extends MiddleLogin {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_LOGIN_ID, version);
		serializer.writeInt(playerEntityId);
		serializer.writeByte(gamemode.getId() | (hardcore ? 0x8 : 0));
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_1)) {
			serializer.writeByte(dimension.getId());
		} else {
			serializer.writeInt(dimension.getId());
		}
		serializer.writeByte(difficulty.getId());
		serializer.writeByte(maxplayers);
		StringSerializer.writeString(serializer, version, leveltype);
		serializer.writeBoolean(reducedDebugInfo);
		return RecyclableSingletonList.create(serializer);
	}

}
