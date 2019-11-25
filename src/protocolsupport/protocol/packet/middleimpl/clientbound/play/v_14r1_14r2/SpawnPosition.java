package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class SpawnPosition extends MiddleSpawnPosition {

	public SpawnPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnposition = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_SPAWN_POSITION);
		PositionSerializer.writePosition(spawnposition, position);
		codec.write(spawnposition);
	}

}
