package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7;

import com.mojang.authlib.properties.Property;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyDataWatcherSerializer;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_NAMED_ID, version);
		serializer.writeVarInt(playerEntityId);
		serializer.writeString(version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		serializer.writeString(name);
		if (version == ProtocolVersion.MINECRAFT_1_7_10) {
			serializer.writeVarInt(properties.size());
			for (Property property : properties) {
				serializer.writeString(property.getName());
				serializer.writeString(property.getValue());
				serializer.writeString(property.getSignature());
			}
		}
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeShort(0);
		LegacyDataWatcherSerializer.encodeData(WatchedDataRemapper.transform(wplayer, metadata, version), serializer);
		return RecyclableSingletonList.create(serializer);
	}

}
