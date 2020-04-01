package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		spawnnamed.writeInt(entity.getId());
		PlayerListEntry entry = cache.getPlayerListCache().getEntry(entity.getUUID());
		StringSerializer.writeString(spawnnamed, version, entry != null ? LegacyChat.clampLegacyText(entry.getCurrentName(cache.getAttributesCache().getLocale()), 16) : "UNKNOWN");
		spawnnamed.writeInt((int) (x * 32));
		spawnnamed.writeInt((int) (y * 32));
		spawnnamed.writeInt((int) (z * 32));
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		spawnnamed.writeShort(0);
		codec.write(spawnnamed);
	}

}
