package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnnamed = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_NAMED);
		VarNumberSerializer.writeVarInt(spawnnamed, entity.getId());
		UUIDSerializer.writeUUID2L(spawnnamed, entity.getUUID());
		spawnnamed.writeDouble(x);
		spawnnamed.writeDouble(y);
		spawnnamed.writeDouble(z);
		spawnnamed.writeByte(yaw);
		spawnnamed.writeByte(pitch);
		NetworkEntityMetadataSerializer.writeData(spawnnamed, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		codec.writeClientbound(spawnnamed);
	}

}
