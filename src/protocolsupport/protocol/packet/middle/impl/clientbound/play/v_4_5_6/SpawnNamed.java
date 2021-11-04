package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.MiscUtils;

public class SpawnNamed extends MiddleSpawnNamed implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public SpawnNamed(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_NAMED);
		spawnnamed.writeInt(entity.getId());
		StringCodec.writeShortUTF16BEString(spawnnamed, MiscUtils.clampString(playerlistEntry.getUserName(), 16));
		spawnnamed.writeInt((int) (x * 32));
		spawnnamed.writeInt((int) (y * 32));
		spawnnamed.writeInt((int) (z * 32));
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		spawnnamed.writeShort(0);
		NetworkEntityMetadataSerializer.writeLegacyData(spawnnamed, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		io.writeClientbound(spawnnamed);
	}

}
