package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16.AbstractRemappedSpawnLiving;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.entity.FlatteningEntityId;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class SpawnLiving extends AbstractRemappedSpawnLiving {

	protected final ArrayBasedIdRemappingTable flatteningEntityIdTable = FlatteningEntityId.REGISTRY.getTable(version);

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING);
		VarNumberSerializer.writeVarInt(spawnliving, entity.getId());
		UUIDSerializer.writeUUID2L(spawnliving, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnliving, flatteningEntityIdTable.getRemap(rType.getNetworkTypeId()));
		spawnliving.writeDouble(x);
		spawnliving.writeDouble(y);
		spawnliving.writeDouble(z);
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headPitch);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		codec.write(spawnliving);
	}

}
