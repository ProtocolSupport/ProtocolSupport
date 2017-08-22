package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;

public abstract class MiddleEntityLeash extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int vehicleId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = serverdata.readInt();
		vehicleId = serverdata.readInt();
	}

	@Override
	public void handle() {
		DataCache data = cache.getWatchedEntity(entityId).getDataCache();
		data.attachedId = vehicleId;
		cache.updateWatchedDataCache(entityId, data);
	}

}
