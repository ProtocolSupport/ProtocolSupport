package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataSpawnObject;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.PrimitiveTypeUtils;

public class SpawnObject extends AbstractPotionItemAsObjectDataSpawnObject implements IClientboundMiddlePacketV8 {

	public SpawnObject(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnObject() {
		io.writeClientbound(create(entity.getId(), fType, x, y, z, pitch, yaw, fObjectdata, velX, velY, velZ));
	}

	public static ClientBoundPacketData create(
		int entityId, NetworkEntityType type,
		double x, double y, double z,
		byte pitch, byte yaw,
		int objectdata,
		short velX, short velY, short velZ
	) {
		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_OBJECT);
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
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL);
		VarNumberCodec.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(x));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(y));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(z));
		io.writeClientbound(spawnglobal);
	}

}
