package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPosition extends MiddleSpawnPosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_POS, connection.getVersion());
		VarNumberSerializer.writeSVarInt(serializer, 1); //1 - sets level spawn position, 0 - sets player respawn position
		PositionSerializer.writePEPosition(serializer, position);
		serializer.writeBoolean(true); //isn't used when setting level spawn position, unknown use for player respawn position
		return RecyclableSingletonList.create(serializer);
	}

}
