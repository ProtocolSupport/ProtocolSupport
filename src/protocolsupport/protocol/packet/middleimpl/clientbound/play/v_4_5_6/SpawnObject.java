package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLocationOffsetSpawnObject;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnObject extends AbstractLocationOffsetSpawnObject {

	public SpawnObject(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnObject() {
		int fpX = (int) (x * 32);
		int fpY = (int) (y * 32);
		int fpZ = (int) (z * 32);
		if (rType == NetworkEntityType.ITEM_FRAME) {
			switch (rObjectdata) {
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
		spawnobject.writeInt(entity.getId());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(rType));
		spawnobject.writeInt(fpX);
		spawnobject.writeInt(fpY);
		spawnobject.writeInt(fpZ);
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(rObjectdata);
		if (rObjectdata > 0) {
			spawnobject.writeShort(motX);
			spawnobject.writeShort(motY);
			spawnobject.writeShort(motZ);
		}
		codec.write(spawnobject);
	}

	@Override
	protected void writeSpawnThunderbolt() {
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL);
		spawnglobal.writeInt(entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeInt((int) (x * 32));
		spawnglobal.writeInt((int) (y * 32));
		spawnglobal.writeInt((int) (z * 32));
		codec.write(spawnglobal);
	}

}
