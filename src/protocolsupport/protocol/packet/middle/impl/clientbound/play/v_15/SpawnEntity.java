package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_15;

import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyThunderboltSpawnEntity;
import protocolsupport.protocol.typeremapper.entity.FlatteningNetworkEntityIdRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class SpawnEntity extends AbstractLegacyThunderboltSpawnEntity implements IClientboundMiddlePacketV15 {

	public SpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable flatteningEntityIdTable = FlatteningNetworkEntityIdRegistry.INSTANCE.getTable(version);

	@Override
	protected void writeSpawnLiving() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_ENTITY);
		VarNumberCodec.writeVarInt(spawnliving, entity.getId());
		UUIDCodec.writeUUID(spawnliving, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnliving, flatteningEntityIdTable.get(fType.getNetworkTypeId()));
		spawnliving.writeDouble(x);
		spawnliving.writeDouble(y);
		spawnliving.writeDouble(z);
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headYaw);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		io.writeClientbound(spawnliving);
	}

	@Override
	protected void writeSpawnObject() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_OBJECT);
		VarNumberCodec.writeVarInt(serializer, entity.getId());
		UUIDCodec.writeUUID(serializer, entity.getUUID());
		serializer.writeByte(flatteningEntityIdTable.get(fType.getNetworkTypeId()));
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(fObjectdata);
		serializer.writeShort(motX);
		serializer.writeShort(motY);
		serializer.writeShort(motZ);
		io.writeClientbound(serializer);
	}

	@Override
	protected void writeSpawnThunderbolt() {
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_GLOBAL);
		VarNumberCodec.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeDouble(x);
		spawnglobal.writeDouble(y);
		spawnglobal.writeDouble(z);
		io.writeClientbound(spawnglobal);
	}

}
