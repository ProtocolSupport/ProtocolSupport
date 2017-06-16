package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleExplosion extends ClientBoundMiddlePacket {

	protected float x;
	protected float y;
	protected float z;
	protected float radius;
	protected Position[] blocks;
	protected float pMotX;
	protected float pMotY;
	protected float pMotZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		radius = serverdata.readFloat();
		blocks = new Position[serverdata.readInt()];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new Position(serverdata.readByte(), serverdata.readByte(), serverdata.readByte());
		}
		pMotX = serverdata.readFloat();
		pMotY = serverdata.readFloat();
		pMotZ = serverdata.readFloat();
	}

}
