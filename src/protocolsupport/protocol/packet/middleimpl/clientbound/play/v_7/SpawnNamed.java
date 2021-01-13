package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(spawnnamed, entity.getId());
		UUID uuid = entity.getUUID();
		StringSerializer.writeVarIntUTF8String(spawnnamed, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		StringSerializer.writeVarIntUTF8String(spawnnamed, Utils.clampString(playerlistEntry.getUserName(), 16));
		if (version == ProtocolVersion.MINECRAFT_1_7_10) {
			MiscSerializer.writeVarIntCountPrefixedType(spawnnamed, playerlistEntry.getProperties(), (to, properties) -> {
				int signedCount = properties.size();
				for (int i = 0; i < properties.size(); i++) {
					ProfileProperty property = properties.get(i);
					if (property.hasSignature()) {
						StringSerializer.writeVarIntUTF8String(to, property.getName());
						StringSerializer.writeVarIntUTF8String(to, property.getValue());
						StringSerializer.writeVarIntUTF8String(to, property.getSignature());
					} else {
						signedCount--;
					}
				}
				return signedCount;
			});
		}
		spawnnamed.writeInt((int) (x * 32));
		spawnnamed.writeInt((int) (y * 32));
		spawnnamed.writeInt((int) (z * 32));
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		spawnnamed.writeShort(0);
		NetworkEntityMetadataSerializer.writeLegacyData(spawnnamed, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		codec.writeClientbound(spawnnamed);
	}

}
