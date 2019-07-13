package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityLeash extends ClientBoundMiddlePacket {

	public MiddleEntityLeash(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected int vehicleId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = serverdata.readInt();
		vehicleId = serverdata.readInt();
	}

	@Override
	public boolean postFromServerRead() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity != null) {
			entity.getDataCache().setAttachedId(vehicleId);
		}
		return true;
	}

}
