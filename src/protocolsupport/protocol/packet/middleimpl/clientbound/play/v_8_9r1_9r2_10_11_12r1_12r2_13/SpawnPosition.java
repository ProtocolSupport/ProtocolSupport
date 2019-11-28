package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

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
		ClientBoundPacketData spawnposition = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_POSITION);
		PositionSerializer.writeLegacyPositionL(spawnposition, position);
		codec.write(spawnposition);
	}

}
