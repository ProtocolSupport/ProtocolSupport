package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleExplosion extends ClientBoundMiddlePacket {

	protected float x;
	protected float y;
	protected float z;
	protected float radius;
	protected AffectedBlock[] blocks;
	protected float pMotX;
	protected float pMotY;
	protected float pMotZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		x = serverdata.readFloat();
		y = serverdata.readFloat();
		z = serverdata.readFloat();
		radius = serverdata.readFloat();
		blocks = new AffectedBlock[serverdata.readInt()];
		for (int i = 0; i < blocks.length; i++) {
			AffectedBlock block = new AffectedBlock();
			block.offX = serverdata.readByte();
			block.offY = serverdata.readByte();
			block.offZ = serverdata.readByte();
			blocks[i] = block;
		}
		pMotX = serverdata.readFloat();
		pMotY = serverdata.readFloat();
		pMotZ = serverdata.readFloat();
	}

	protected static class AffectedBlock {
		public int offX;
		public int offY;
		public int offZ;
	}

}
