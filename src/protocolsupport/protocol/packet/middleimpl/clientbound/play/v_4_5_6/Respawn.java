package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Respawn extends MiddleRespawn {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_RESPAWN_ID, version);
		serializer.writeInt(dimension.getId());
		serializer.writeByte(difficulty.getId());
		serializer.writeByte(gamemode.getId());
		serializer.writeShort(256);
		StringSerializer.writeString(serializer, version, leveltype);
		return RecyclableSingletonList.create(serializer);
	}

}
