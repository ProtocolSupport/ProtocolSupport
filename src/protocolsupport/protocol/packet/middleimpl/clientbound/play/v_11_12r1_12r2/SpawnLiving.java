package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractRemappedSpawnLiving;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.i18n.I18NData;

public class SpawnLiving extends AbstractRemappedSpawnLiving {

	public SpawnLiving(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_LIVING);
		VarNumberSerializer.writeVarInt(spawnliving, entity.getId());
		UUIDSerializer.writeUUID2L(spawnliving, entity.getUUID());
		VarNumberSerializer.writeVarInt(spawnliving, LegacyEntityId.getIntId(fType));
		spawnliving.writeDouble(x);
		spawnliving.writeDouble(y);
		spawnliving.writeDouble(z);
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headPitch);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		NetworkEntityMetadataSerializer.writeData(spawnliving, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		codec.writeClientbound(spawnliving);
	}

}
