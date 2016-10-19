package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Respawn extends MiddleRespawn<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_RESPAWN_ID, version);
		serializer.writeInt(dimension);
		serializer.writeByte(difficulty);
		serializer.writeByte(gamemode);
		serializer.writeString(leveltype);
		return RecyclableSingletonList.create(serializer);
	}

}
