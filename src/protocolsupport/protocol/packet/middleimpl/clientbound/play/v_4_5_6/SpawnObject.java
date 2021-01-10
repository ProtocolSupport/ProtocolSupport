package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataSpawnObject;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.PrimitiveTypeUtils;

public class SpawnObject extends AbstractPotionItemAsObjectDataSpawnObject {

	public SpawnObject(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnObject() {
		codec.writeClientbound(create(entity.getId(), fType, x, y, z, pitch, yaw, rObjectdata, velX, velY, velZ));
	}

	public static ClientBoundPacketData create(
		int entityId, NetworkEntityType type,
		double x, double y, double z,
		byte pitch, byte yaw,
		int objectdata,
		short velX, short velY, short velZ
	) {
		int fpX = PrimitiveTypeUtils.toFixedPoint32(x);
		int fpY = PrimitiveTypeUtils.toFixedPoint32(y);
		int fpZ = PrimitiveTypeUtils.toFixedPoint32(z);

		if (type == NetworkEntityType.ITEM_FRAME) {
			switch (objectdata) {
				case 0: {
					fpZ -= 32;
					yaw = (byte) 128;
					break;
				}
				case 1: {
					fpX += 32;
					yaw = 64;
					break;
				}
				case 2: {
					fpZ += 32;
					yaw = 0;
					break;
				}
				case 3: {
					fpX -= 32;
					yaw = (byte) 192;
					break;
				}
			}
		}

		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		spawnobject.writeInt(entityId);
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(type));
		spawnobject.writeInt(fpX);
		spawnobject.writeInt(fpY);
		spawnobject.writeInt(fpZ);
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
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL);
		spawnglobal.writeInt(entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(x));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(y));
		spawnglobal.writeInt(PrimitiveTypeUtils.toFixedPoint32(z));
		codec.writeClientbound(spawnglobal);
	}

}
