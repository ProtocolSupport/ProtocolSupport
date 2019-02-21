package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.List;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_NAMED_ID);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		UUID uuid = entity.getUUID();
		StringSerializer.writeString(serializer, version, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		PlayerListEntry entry = cache.getPlayerListCache().getEntry(uuid);
		if (entry != null) {
			StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(entry.getCurrentName(cache.getAttributesCache().getLocale()), 16));
			if (version == ProtocolVersion.MINECRAFT_1_7_10) {
				List<ProfileProperty> properties = entry.getProperties(true);
				VarNumberSerializer.writeVarInt(serializer, properties.size());
				for (ProfileProperty property : properties) {
					StringSerializer.writeString(serializer, version, property.getName());
					StringSerializer.writeString(serializer, version, property.getValue());
					StringSerializer.writeString(serializer, version, property.getSignature());
				}
			}
		} else {
			StringSerializer.writeString(serializer, version, "UNKNOWN");
			if (version == ProtocolVersion.MINECRAFT_1_7_10) {
				VarNumberSerializer.writeVarInt(serializer, 0);
			}
		}
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeShort(0);
		DataWatcherSerializer.writeLegacyData(serializer, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		return RecyclableSingletonList.create(serializer);
	}

}
