package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemSpawnEntiy;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.PrimitiveTypeUtils;
import protocolsupport.protocol.utils.i18n.I18NData;

public class SpawnEntity extends AbstractLegacyPotionItemSpawnEntiy implements IClientboundMiddlePacketV8 {

	public SpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnLiving() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_ENTITY);
		VarNumberCodec.writeVarInt(spawnliving, entity.getId());
		spawnliving.writeByte(LegacyEntityId.getIntId(fType));
		spawnliving.writeInt((int) (x * 32));
		spawnliving.writeInt((int) (y * 32));
		spawnliving.writeInt((int) (z * 32));
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headYaw);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		NetworkEntityMetadataCodec.writeLegacyData(spawnliving, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		io.writeClientbound(spawnliving);
	}

	@Override
	protected void writeSpawnObject() {
		io.writeClientbound(createSpawnObject(entity.getId(), fType, x, y, z, pitch, yaw, fObjectdata, motX, motY, motZ));
	}

	public static ClientBoundPacketData createSpawnObject(
		int entityId, NetworkEntityType type,
		double x, double y, double z,
		byte pitch, byte yaw,
		int objectdata,
		short velX, short velY, short velZ
	) {
		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_OBJECT);
		VarNumberCodec.writeVarInt(spawnobject, entityId);
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(type));
		spawnobject.writeInt(PrimitiveTypeUtils.toFixedPoint32(x));
		spawnobject.writeInt(PrimitiveTypeUtils.toFixedPoint32(y));
		spawnobject.writeInt(PrimitiveTypeUtils.toFixedPoint32(z));
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(objectdata);
		if (objectdata > 0) {
			spawnobject.writeShort(velX);
			spawnobject.writeShort(velY);
			spawnobject.writeShort(velZ);
		}
		return spawnobject;
	}

	@Override
	protected void writeSpawnThunderbolt() {
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_GLOBAL);
		VarNumberCodec.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(x));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(y));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(z));
		io.writeClientbound(spawnglobal);
	}

}
