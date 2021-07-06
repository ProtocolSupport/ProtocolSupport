package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	public SpawnExpOrb(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnexporb = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_EXP_ORB);
		VarNumberCodec.writeVarInt(spawnexporb, entity.getId());
		spawnexporb.writeDouble(x);
		spawnexporb.writeDouble(y);
		spawnexporb.writeDouble(z);
		spawnexporb.writeShort(count);
		codec.writeClientbound(spawnexporb);
	}

}
