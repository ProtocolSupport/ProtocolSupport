package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Respawn extends MiddleRespawn<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_RESPAWN_ID, version);
		serializer.writeInt(dimension);
		serializer.writeByte(difficulty);
		serializer.writeByte(gamemode);
		serializer.writeShort(256);
		serializer.writeString(leveltype);
		return RecyclableSingletonList.create(serializer);
	}

}
