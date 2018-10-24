package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

	protected final EntityRemapper entityRemapper = new EntityRemapper(connection.getVersion());

	public MiddleSpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected int headPitch;
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
		yaw = serverdata.readUnsignedByte();
		pitch = serverdata.readUnsignedByte();
		headPitch = serverdata.readUnsignedByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		entityRemapper.readEntityWithMetadata(cache.getAttributesCache().getLocale(), entity, serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		if (!GenericIdSkipper.ENTITY.getTable(connection.getVersion()).shouldSkip(entity.getType())) {
			cache.getWatchedEntityCache().addWatchedEntity(entity);
			entityRemapper.remap(true);
			return true;
		} else {
			return false;
		}
	}

}
