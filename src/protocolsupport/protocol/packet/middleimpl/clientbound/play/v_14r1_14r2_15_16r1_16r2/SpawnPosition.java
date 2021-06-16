package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

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
		ClientBoundPacketData spawnposition = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_POSITION);
		PositionSerializer.writePosition(spawnposition, position);
		codec.writeClientbound(spawnposition);
	}

}
