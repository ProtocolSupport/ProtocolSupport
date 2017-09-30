package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;

public abstract class MiddleSpawnExpOrb extends ClientBoundMiddlePacket {

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected int count;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entity = new NetworkEntity(null, VarNumberSerializer.readVarInt(serverdata), NetworkEntityType.EXP_ORB);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		count = serverdata.readShort();
	}

	@Override
	public boolean postFromServerRead() {
		cache.addWatchedEntity(entity);
		return true;
	}

}
