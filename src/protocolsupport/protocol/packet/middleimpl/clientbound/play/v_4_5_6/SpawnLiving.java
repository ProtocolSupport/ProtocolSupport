package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;

public class SpawnLiving extends MiddleSpawnLiving {

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING);
		spawnliving.writeInt(entity.getId());
		spawnliving.writeByte(LegacyEntityId.getIntId(entityRemapper.getRemappedEntityType()));
		spawnliving.writeInt((int) (x * 32));
		spawnliving.writeInt((int) (y * 32));
		spawnliving.writeInt((int) (z * 32));
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headPitch);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		NetworkEntityMetadataSerializer.writeLegacyData(spawnliving, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		codec.write(spawnliving);
	}

}
