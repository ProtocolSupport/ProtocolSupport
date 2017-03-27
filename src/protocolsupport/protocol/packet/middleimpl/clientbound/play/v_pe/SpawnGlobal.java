package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeVarInt(serializer, 93); //Lightning is always entity 93. No remap. No worries.
		MiscSerializer.writeLFloat(serializer, (float) x);
		MiscSerializer.writeLFloat(serializer, (float) y);
		MiscSerializer.writeLFloat(serializer, (float) z);
		MiscSerializer.writeLFloat(serializer, 0);  //Well luckily lightning doesn't need much data.
		MiscSerializer.writeLFloat(serializer, 0);
		MiscSerializer.writeLFloat(serializer, 0);
		MiscSerializer.writeLFloat(serializer, 0);
		MiscSerializer.writeLFloat(serializer, 0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		return RecyclableSingletonList.create(serializer);
	}
}
