package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractRemappedSpawnEntity;
import protocolsupport.protocol.typeremapper.entity.FlatteningNetworkEntityIdRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class SpawnEntity extends AbstractRemappedSpawnEntity implements
IClientboundMiddlePacketV20 {

	public SpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable flatteningEntityIdTable = FlatteningNetworkEntityIdRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData spawnentityPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_ENTITY);
		VarNumberCodec.writeVarInt(spawnentityPacket, entity.getId());
		UUIDCodec.writeUUID(spawnentityPacket, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnentityPacket, flatteningEntityIdTable.get(fType.getNetworkTypeId()));
		spawnentityPacket.writeDouble(x);
		spawnentityPacket.writeDouble(y);
		spawnentityPacket.writeDouble(z);
		spawnentityPacket.writeByte(pitch);
		spawnentityPacket.writeByte(yaw);
		spawnentityPacket.writeByte(headYaw);
		VarNumberCodec.writeVarInt(spawnentityPacket, objectdata);
		spawnentityPacket.writeShort(motX);
		spawnentityPacket.writeShort(motY);
		spawnentityPacket.writeShort(motZ);
		io.writeClientbound(spawnentityPacket);
	}

}
