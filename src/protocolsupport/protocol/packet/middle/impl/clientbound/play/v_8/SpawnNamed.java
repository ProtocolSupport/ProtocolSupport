package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.utils.i18n.I18NData;

public class SpawnNamed extends MiddleSpawnNamed implements IClientboundMiddlePacketV8 {

	public SpawnNamed(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_NAMED);
		VarNumberCodec.writeVarInt(spawnnamed, entity.getId());
		UUIDCodec.writeUUID(spawnnamed, entity.getUUID());
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
