package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.List;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.legacy.LegacyDataWatcherSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_NAMED_ID);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		UUID uuid = entity.getUUID();
		StringSerializer.writeString(serializer, version, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		PlayerListEntry entry = cache.getPlayerListCache().getEntry(uuid);
		StringSerializer.writeString(serializer, version, entry.getName(cache.getAttributesCache().getLocale()));
		if (version == ProtocolVersion.MINECRAFT_1_7_10) {
			List<ProfileProperty> properties = entry.getProperties(true);
			VarNumberSerializer.writeVarInt(serializer, properties.size());
			for (ProfileProperty property : properties) {
				StringSerializer.writeString(serializer, version, property.getName());
				StringSerializer.writeString(serializer, version, property.getValue());
				StringSerializer.writeString(serializer, version, property.getSignature());
			}
		}
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeShort(0);
		LegacyDataWatcherSerializer.encodeData(serializer, version, cache.getAttributesCache().getLocale(), metadata.getRemapped());
		return RecyclableSingletonList.create(serializer);
	}

}
