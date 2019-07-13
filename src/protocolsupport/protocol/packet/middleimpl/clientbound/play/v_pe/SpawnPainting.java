package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting {

	public SpawnPainting(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_PAINTING);
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
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
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			serializer.writeFloatLE(mcpeX);
			serializer.writeFloatLE(position.getY());
			serializer.writeFloatLE(mcpeZ);
		} else {
			VarNumberSerializer.writeSVarInt(serializer, mcpeX);
			VarNumberSerializer.writeVarInt(serializer, position.getY());
			VarNumberSerializer.writeSVarInt(serializer, mcpeZ);
		}
		VarNumberSerializer.writeSVarInt(serializer, direction);
		//TODO: Fix Type
		StringSerializer.writeString(serializer, connection.getVersion(), "" + type);
		return RecyclableSingletonList.create(serializer);
	}
}
