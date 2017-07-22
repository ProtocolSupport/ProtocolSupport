package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_PAINTING, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		// In MCPE the paintings are always +1 blocks ahead where it should be, so we need to edit the coordinates a bit
		int mcpeX = position.getX();
		int mcpeZ = position.getZ();
		switch (direction) {
			case 0: { // South
				mcpeZ -= 1;
				break;
			}
			case 1: { // West
				mcpeX += 1;
				break;
			}
			case 2: { // North
				mcpeZ += 1;
				break;
			}
			case 3: { // East
				mcpeX -= 1;
				break;
			}
		}
		VarNumberSerializer.writeSVarInt(serializer, mcpeX);
		VarNumberSerializer.writeVarInt(serializer, position.getY());
		VarNumberSerializer.writeSVarInt(serializer, mcpeZ);
		VarNumberSerializer.writeSVarInt(serializer, direction);
		StringSerializer.writeString(serializer, version, type); // The painting names seems to be the same in both versions
		return RecyclableSingletonList.create(serializer);
	}
}
