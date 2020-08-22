package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class MiddleSpawnExpOrb extends ClientBoundMiddlePacket {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleSpawnExpOrb(MiddlePacketInit init) {
		super(init);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int count;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		entity = new NetworkEntity(null, VarNumberSerializer.readVarInt(serverdata), NetworkEntityType.EXP_ORB);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		count = serverdata.readShort();
	}

	@Override
	protected void handleReadData() {
		entity.getDataCache().setLocation(x, y, z, (byte) 0, (byte) 0);

		entityCache.addEntity(entity);
	}

}
