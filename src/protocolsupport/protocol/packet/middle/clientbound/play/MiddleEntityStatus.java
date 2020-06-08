package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

//TODO: Enum for status id?
public abstract class MiddleEntityStatus extends ClientBoundMiddlePacket {

	protected static final int STATUS_LIVING_DEATH = 3;

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleEntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected int status;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		entity = entityCache.getEntity(serverdata.readInt());
		status = serverdata.readUnsignedByte();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
