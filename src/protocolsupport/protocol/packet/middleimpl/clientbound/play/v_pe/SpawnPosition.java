package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPosition extends MiddleSpawnPosition {

	private static final int TYPE_PLAYER_SPAWN = 0;
	private static final int TYPE_WORLD_SPAWN = 1;

	public SpawnPosition(ConnectionImpl connection) {
		super(connection);
	}

	private static ClientBoundPacketData setSpawn(Position position, int type, boolean forcedSpawn) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_POS);
		VarNumberSerializer.writeSVarInt(serializer, type);
		PositionSerializer.writePEPosition(serializer, position);
		serializer.writeBoolean(forcedSpawn);
		return serializer;
	}

	public static ClientBoundPacketData setPlayerSpawn(Position position) {
		return setSpawn(position, TYPE_PLAYER_SPAWN, false);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(setSpawn(position, TYPE_WORLD_SPAWN, true));
	}

}
