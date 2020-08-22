package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public class SpawnNamed extends MiddleSpawnNamed {

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();

	public SpawnNamed(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(spawnnamed, entity.getId());
		UUID uuid = entity.getUUID();
		StringSerializer.writeVarIntUTF8String(spawnnamed, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		PlayerListEntry entry = playerlistCache.getEntry(uuid);
		if (entry != null) {
			StringSerializer.writeVarIntUTF8String(spawnnamed, Utils.clampString(entry.getUserName(), 16));
			if (version == ProtocolVersion.MINECRAFT_1_7_10) {
				ArraySerializer.writeVarIntTArray(spawnnamed, entry.getProperties(true), (to, property) -> {
					StringSerializer.writeVarIntUTF8String(to, property.getName());
					StringSerializer.writeVarIntUTF8String(to, property.getValue());
					StringSerializer.writeVarIntUTF8String(to, property.getSignature());
				});
			}
		} else {
			StringSerializer.writeVarIntUTF8String(spawnnamed, "UNKNOWN");
			if (version == ProtocolVersion.MINECRAFT_1_7_10) {
				VarNumberSerializer.writeVarInt(spawnnamed, 0);
			}
		}
		spawnnamed.writeInt((int) (x * 32));
		spawnnamed.writeInt((int) (y * 32));
		spawnnamed.writeInt((int) (z * 32));
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		spawnnamed.writeShort(0);
		NetworkEntityMetadataSerializer.writeLegacyData(spawnnamed, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		codec.write(spawnnamed);
	}

}
