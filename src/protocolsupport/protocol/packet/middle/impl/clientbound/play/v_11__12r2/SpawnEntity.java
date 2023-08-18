package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_11__12r2;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyThunderboltSpawnEntity;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.i18n.I18NData;

public class SpawnEntity extends AbstractLegacyThunderboltSpawnEntity implements
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public SpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeSpawnLiving() {
		ClientBoundPacketData spawnliving = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SPAWN_ENTITY);
		VarNumberCodec.writeVarInt(spawnliving, entity.getId());
		UUIDCodec.writeUUID(spawnliving, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnliving, LegacyEntityId.getIntId(fType));
		spawnliving.writeDouble(x);
		spawnliving.writeDouble(y);
		spawnliving.writeDouble(z);
		spawnliving.writeByte(yaw);
		spawnliving.writeByte(pitch);
		spawnliving.writeByte(headYaw);
		spawnliving.writeShort(motX);
		spawnliving.writeShort(motY);
		spawnliving.writeShort(motZ);
		NetworkEntityMetadataCodec.writeData(spawnliving, version, I18NData.DEFAULT_LOCALE, NetworkEntityMetadataList.EMPTY);
		io.writeClientbound(spawnliving);
	}

	@Override
	protected void writeSpawnObject() {
		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_OBJECT);
		VarNumberCodec.writeVarInt(spawnobject, entity.getId());
		UUIDCodec.writeUUID(spawnobject, entity.getUUID());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(fType));
		spawnobject.writeDouble(x);
		spawnobject.writeDouble(y);
		spawnobject.writeDouble(z);
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(fObjectdata);
		spawnobject.writeShort(motX);
		spawnobject.writeShort(motY);
		spawnobject.writeShort(motZ);
		io.writeClientbound(spawnobject);
	}

	@Override
	protected void writeSpawnThunderbolt() {
		ClientBoundPacketData spawnglobal = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_GLOBAL);
		VarNumberCodec.writeVarInt(spawnglobal, entity.getId());
		spawnglobal.writeByte(1);
		spawnglobal.writeDouble(x);
		spawnglobal.writeDouble(y);
		spawnglobal.writeDouble(z);
		io.writeClientbound(spawnglobal);
	}

}
