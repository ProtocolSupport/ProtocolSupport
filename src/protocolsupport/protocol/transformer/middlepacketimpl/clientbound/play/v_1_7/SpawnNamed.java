package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.util.Collection;
import java.util.Collections;

import com.mojang.authlib.properties.Property;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.DataWatcherSerializer;

public class SpawnNamed extends MiddleSpawnNamed<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
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
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeShort(itemId);
		serializer.writeBytes(DataWatcherSerializer.encodeData(version, WatchedDataRemapper.transform(wplayer, metadata, version)));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_SPAWN_NAMED_ID, serializer));
	}

}
