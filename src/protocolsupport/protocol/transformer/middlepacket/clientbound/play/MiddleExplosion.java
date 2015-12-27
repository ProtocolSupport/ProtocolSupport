package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleExplosion<T> extends ClientBoundMiddlePacket<T> {

	protected float x;
	protected float y;
	protected float z;
	protected float radius;
	protected AffectedBlock[] blocks;
	protected float pMotX;
	protected float pMotY;
	protected float pMotZ;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		x = serializer.readFloat();
		y = serializer.readFloat();
		z = serializer.readFloat();
		radius = serializer.readFloat();
		blocks = new AffectedBlock[serializer.readInt()];
		for (int i = 0; i < blocks.length; i++) {
			AffectedBlock block = new AffectedBlock();
			block.offX = serializer.readByte();
			block.offY = serializer.readByte();
			block.offZ = serializer.readByte();
			blocks[i] = block;
		}
		pMotX = serializer.readFloat();
		pMotY = serializer.readFloat();
		pMotZ = serializer.readFloat();
	}

	protected static class AffectedBlock {
		public int offX;
		public int offY;
		public int offZ;
	}

}
