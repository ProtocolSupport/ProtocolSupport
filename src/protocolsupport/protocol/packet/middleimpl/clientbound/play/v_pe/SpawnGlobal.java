package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(SpawnGlobal.create(version, entityId, x, y, z, 93));
	}
	
	protected static ClientBoundPacketData create(ProtocolVersion version, int entityId, double x, double y, double z, int entityType) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		VarNumberSerializer.writeVarInt(serializer, entityType);
		MiscSerializer.writeLFloat(serializer, (float) x);
		MiscSerializer.writeLFloat(serializer, (float) y);
		MiscSerializer.writeLFloat(serializer, (float) z);
		MiscSerializer.writeLFloat(serializer, 0); //mot x, not used
		MiscSerializer.writeLFloat(serializer, 0); //mot y, not used
		MiscSerializer.writeLFloat(serializer, 0); //mot z, not used
		MiscSerializer.writeLFloat(serializer, 0); //pitch, not used
		MiscSerializer.writeLFloat(serializer, 0); //yaw, not used
		VarNumberSerializer.writeVarInt(serializer, 0); //attributes, not used
		VarNumberSerializer.writeVarInt(serializer, 0); //metadata, not used
		VarNumberSerializer.writeVarInt(serializer, 0); //links, not used
		return serializer;
	}
}
