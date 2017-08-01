package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleEntityVelocity extends MiddleEntity {

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
	}
	
	@Override
	public void handle() {
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if(entity != null) {
			entity.updateVelocity(new Vector(motX, motY, motZ));
		}
	}

}
