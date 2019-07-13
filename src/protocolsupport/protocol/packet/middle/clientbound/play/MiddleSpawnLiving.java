package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

	protected final EntityRemapper entityRemapper = new EntityRemapper(version);

	public MiddleSpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected byte headYaw;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = VarNumberSerializer.readVarInt(serverdata);
		entity = NetworkEntity.createMob(uuid, entityId, typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		headYaw = serverdata.readByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		entityRemapper.readEntityWithMetadata(entity, serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		if (!GenericIdSkipper.ENTITY.getTable(version).shouldSkip(entity.getType())) {
			cache.getWatchedEntityCache().addWatchedEntity(entity);
			entityRemapper.remap(true);
			return true;
		} else {
			return false;
		}
	}

}
