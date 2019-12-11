package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnLiving extends MiddleSpawnLiving {

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient0(NetworkEntityType remappedEntityType, NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING);
		VarNumberSerializer.writeVarInt(spawnliving, entity.getId());
		MiscSerializer.writeUUID(spawnliving, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnliving, LegacyEntityId.getIntId(remappedEntityType));
		spawnliving.writeDouble(x);
		spawnliving.writeDouble(y);
		spawnliving.writeDouble(z);
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headPitch);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		NetworkEntityMetadataSerializer.writeData(spawnliving, version, cache.getAttributesCache().getLocale(), remappedMetadata);
		codec.write(spawnliving);
	}

}
