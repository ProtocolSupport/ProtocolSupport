package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class SpawnPosition extends MiddleSpawnPosition {

	public SpawnPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnposition = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SPAWN_POSITION);
		PositionSerializer.writeLegacyPositionI(spawnposition, position);
		codec.writeClientbound(spawnposition);
	}

}
