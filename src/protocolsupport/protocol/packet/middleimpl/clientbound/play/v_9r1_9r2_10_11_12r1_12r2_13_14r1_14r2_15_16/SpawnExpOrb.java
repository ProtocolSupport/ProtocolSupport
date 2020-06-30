package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	public SpawnExpOrb(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData spawnexporb = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_EXP_ORB);
		VarNumberSerializer.writeVarInt(spawnexporb, entity.getId());
		spawnexporb.writeDouble(x);
		spawnexporb.writeDouble(y);
		spawnexporb.writeDouble(z);
		spawnexporb.writeShort(count);
		codec.write(spawnexporb);
	}

}
