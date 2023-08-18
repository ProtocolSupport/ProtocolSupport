package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.MiscUtils;

public class SpawnNamed extends MiddleSpawnNamed implements IClientboundMiddlePacketV7 {

	public SpawnNamed(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_NAMED);
		VarNumberCodec.writeVarInt(spawnnamed, entity.getId());
		UUID uuid = entity.getUUID();
		StringCodec.writeVarIntUTF8String(spawnnamed, version == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
		StringCodec.writeVarIntUTF8String(spawnnamed, MiscUtils.clampString(playerlistEntry.getUserName(), 16));
		if (version == ProtocolVersion.MINECRAFT_1_7_10) {
			MiscDataCodec.writeVarIntCountPrefixedType(spawnnamed, playerlistEntry.getProperties(), (to, properties) -> {
				int signedCount = 0;
				for (ProfileProperty property : properties) {
					if (property.hasSignature()) {
						StringCodec.writeVarIntUTF8String(to, property.getName());
						StringCodec.writeVarIntUTF8String(to, property.getValue());
						StringCodec.writeVarIntUTF8String(to, property.getSignature());
						signedCount++;
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
		NetworkEntityMetadataCodec.writeLegacyData(spawnnamed, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		io.writeClientbound(spawnnamed);
	}

}
