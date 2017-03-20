package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_PLAYER, version);
		MiscSerializer.writeUUID(serializer, uuid);
		StringSerializer.writeString(serializer, version, name);
		VarNumberSerializer.writeSVarLong(serializer, playerEntityId);
		VarNumberSerializer.writeSVarLong(serializer, playerEntityId);
		MiscSerializer.writeLFloat(serializer, (float) x);
		MiscSerializer.writeLFloat(serializer, (float) y);
		MiscSerializer.writeLFloat(serializer, (float) z);
		MiscSerializer.writeLFloat(serializer, 0F); //mot x
		MiscSerializer.writeLFloat(serializer, 0F); //mot y
		MiscSerializer.writeLFloat(serializer, 0F); //mot z
		MiscSerializer.writeLFloat(serializer, pitch);
		MiscSerializer.writeLFloat(serializer, yaw); //head yaw actually
		MiscSerializer.writeLFloat(serializer, yaw);
		VarNumberSerializer.writeSVarInt(serializer, 0); //held itemstack (it is actually a slot, but we only send null itemstack here, so we only write 0 id)
		VarNumberSerializer.writeSVarInt(serializer, 0); //TODO: metadata
		return RecyclableSingletonList.create(serializer);
	}

}
