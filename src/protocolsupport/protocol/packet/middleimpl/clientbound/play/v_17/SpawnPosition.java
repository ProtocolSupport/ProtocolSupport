package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SpawnPosition extends MiddleSpawnPosition {

	public SpawnPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnpositionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_POSITION);
		PositionCodec.writePosition(spawnpositionPacket, position);
		spawnpositionPacket.writeFloat(angle);
		codec.writeClientbound(spawnpositionPacket);
	}

}
