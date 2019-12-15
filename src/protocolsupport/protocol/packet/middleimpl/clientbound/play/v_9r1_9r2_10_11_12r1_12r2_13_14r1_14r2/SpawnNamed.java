package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient0() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(spawnnamed, entity.getId());
		MiscSerializer.writeUUID(spawnnamed, entity.getUUID());
		spawnnamed.writeDouble(x);
		spawnnamed.writeDouble(y);
		spawnnamed.writeDouble(z);
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		NetworkEntityMetadataSerializer.writeData(spawnnamed, version, cache.getAttributesCache().getLocale(), NetworkEntityMetadataList.EMPTY);
		codec.write(spawnnamed);
	}

}
