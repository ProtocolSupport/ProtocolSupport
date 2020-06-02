package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

//TODO: Enum for status id?
public abstract class MiddleEntityStatus extends ClientBoundMiddlePacket {

	protected static final int STATUS_LIVING_DEATH = 3;

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	public MiddleEntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected int status;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entity = entityCache.getWatchedEntity(serverdata.readInt());
		status = serverdata.readUnsignedByte();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
