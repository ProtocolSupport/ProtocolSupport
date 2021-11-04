package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractThunderboltSpawnObject;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;

public class SpawnObject extends AbstractThunderboltSpawnObject implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	public SpawnObject(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnObject() {
		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_OBJECT);
		VarNumberCodec.writeVarInt(spawnobject, entity.getId());
		UUIDCodec.writeUUID2L(spawnobject, entity.getUUID());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(fType));
		spawnobject.writeDouble(x);
		spawnobject.writeDouble(y);
		spawnobject.writeDouble(z);
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(fObjectdata);
		spawnobject.writeShort(velX);
		spawnobject.writeShort(velY);
		spawnobject.writeShort(velZ);
		io.writeClientbound(spawnobject);
	}

	@Override
	protected void writeSpawnThunderbolt() {
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL);
		VarNumberCodec.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeDouble(x);
		spawnglobal.writeDouble(y);
		spawnglobal.writeDouble(z);
		io.writeClientbound(spawnglobal);
	}

}
