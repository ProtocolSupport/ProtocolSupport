package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPosition extends MiddleSpawnPosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_POS, version);
		VarNumberSerializer.writeSVarInt(serializer, 1); //1 - sets level spawn position, 0 - sets player respawn position?
		VarNumberSerializer.writeSVarInt(serializer, position.getX());
		VarNumberSerializer.writeVarInt(serializer, position.getY());
		VarNumberSerializer.writeSVarInt(serializer, position.getZ());
		serializer.writeBoolean(true); //isn't used when setting level spawn position, unknown use for player respawn position
		return RecyclableSingletonList.create(serializer);
	}

}
