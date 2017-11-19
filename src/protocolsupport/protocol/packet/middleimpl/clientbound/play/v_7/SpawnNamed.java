package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDataWatcherSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_NAMED_ID, version);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		StringSerializer.writeString(serializer, version, version == ProtocolVersion.MINECRAFT_1_7_10 ? entity.getUUID().toString() : entity.getUUID().toString().replace("-", ""));
		StringSerializer.writeString(serializer, version, name);
		if (version == ProtocolVersion.MINECRAFT_1_7_10) {
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
		LegacyDataWatcherSerializer.encodeData(serializer, version, cache.getLocale(), metadata.getRemapped());
		return RecyclableSingletonList.create(serializer);
	}

}
