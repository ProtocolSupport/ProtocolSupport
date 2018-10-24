package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;

public abstract class MiddleSpawnObject extends ClientBoundMiddlePacket {

	protected final EntityRemapper entityRemapper = new EntityRemapper(connection.getVersion());
	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(connection.getVersion());

	public MiddleSpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int pitch;
	protected int yaw;
	protected int objectdata;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = serverdata.readUnsignedByte();
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		pitch = serverdata.readUnsignedByte();
		yaw = serverdata.readUnsignedByte();
		objectdata = serverdata.readInt();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		entity = NetworkEntity.createObject(uuid, entityId, typeId, objectdata);
		entityRemapper.readEntity(entity);
	}

	@Override
	public boolean postFromServerRead() {
		if (!GenericIdSkipper.ENTITY.getTable(connection.getVersion()).shouldSkip(entity.getType())) {
			cache.getWatchedEntityCache().addWatchedEntity(entity);
			entityRemapper.remap(false);
			return true;
		} else {
			return false;
		}
	}

}
