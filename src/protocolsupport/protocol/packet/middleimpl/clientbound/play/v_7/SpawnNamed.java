package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		UUID uuid = entity.getUUID();
		StringSerializer.writeVarIntUTF8String(serializer, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		PlayerListEntry entry = cache.getPlayerListCache().getEntry(uuid);
		if (entry != null) {
			StringSerializer.writeVarIntUTF8String(serializer, Utils.clampString(entry.getUserName(), 16));
			if (version == ProtocolVersion.MINECRAFT_1_7_10) {
				ArraySerializer.writeVarIntTArray(serializer, entry.getProperties(true), (to, property) -> {
					StringSerializer.writeVarIntUTF8String(serializer, property.getName());
					StringSerializer.writeVarIntUTF8String(serializer, property.getValue());
					StringSerializer.writeVarIntUTF8String(serializer, property.getSignature());
				});
			}
		} else {
			StringSerializer.writeVarIntUTF8String(serializer, "UNKNOWN");
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
		NetworkEntityMetadataSerializer.writeLegacyData(serializer, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		return RecyclableSingletonList.create(serializer);
	}

}
