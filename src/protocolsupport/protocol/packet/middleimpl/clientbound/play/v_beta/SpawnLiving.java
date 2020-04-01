package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnLiving extends MiddleSpawnLiving {

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0(NetworkEntityType remappedEntityType) {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING);
		spawnliving.writeInt(entity.getId());
		spawnliving.writeByte(LegacyEntityId.getIntId(remappedEntityType));
		spawnliving.writeInt((int) (x * 32));
		spawnliving.writeInt((int) (y * 32));
		spawnliving.writeInt((int) (z * 32));
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		NetworkEntityMetadataSerializer.writeLegacyData(spawnliving, version, cache.getAttributesCache().getLocale(), NetworkEntityMetadataList.EMPTY);
		codec.write(spawnliving);
	}

}
