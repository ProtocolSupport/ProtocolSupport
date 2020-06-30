package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(spawnnamed, entity.getId());
		UUIDSerializer.writeUUID2L(spawnnamed, entity.getUUID());
		spawnnamed.writeDouble(x);
		spawnnamed.writeDouble(y);
		spawnnamed.writeDouble(z);
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		codec.write(spawnnamed);
	}

}
