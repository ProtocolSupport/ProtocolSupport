package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (type == 70) {
			int id = IdRemapper.BLOCK.getTable(version).getRemap(objectdata & 4095);
			int data = (objectdata >> 12) & 0xF;
			objectdata = (data << 12) | id;
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeByte(IdRemapper.ENTITY_OBJECT.getTable(version).getRemap(type));
		serializer.writeInt((int) x * 32);
		serializer.writeInt((int) y * 32);
		serializer.writeInt((int) z * 32);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
