package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class MiddleSpawnExpOrb extends ClientBoundMiddlePacket {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	public MiddleSpawnExpOrb(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int count;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entity = new NetworkEntity(null, VarNumberSerializer.readVarInt(serverdata), NetworkEntityType.EXP_ORB);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		count = serverdata.readShort();
	}

	@Override
	public void handleReadData() {
		entityCache.addWatchedEntity(entity);
	}

}
