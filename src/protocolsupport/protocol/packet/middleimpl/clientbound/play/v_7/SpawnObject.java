package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLocationOffsetSpawnObject;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnObject extends AbstractLocationOffsetSpawnObject {

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
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
		VarNumberSerializer.writeVarInt(spawnobject, entity.getId());
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

}
