package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class SpawnPosition extends MiddleSpawnPosition {

	public SpawnPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnposition = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_POSITION);
		position.modifyY(1);
		PositionSerializer.writeLegacyPositionI(spawnposition, position);
		codec.writeClientbound(spawnposition);
	}

}
