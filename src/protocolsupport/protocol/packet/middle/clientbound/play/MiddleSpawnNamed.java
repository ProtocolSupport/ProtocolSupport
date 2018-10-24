package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;

public abstract class MiddleSpawnNamed extends ClientBoundMiddlePacket {

	protected final EntityRemapper entityRemapper = new EntityRemapper(connection.getVersion());

	public MiddleSpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int playerEntityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		entity = NetworkEntity.createPlayer(uuid, playerEntityId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readUnsignedByte();
		pitch = serverdata.readUnsignedByte();
		entityRemapper.readEntityWithMetadata(cache.getAttributesCache().getLocale(), entity, serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().addWatchedEntity(entity);
		entityRemapper.remap(true);
		return true;
	}

}
