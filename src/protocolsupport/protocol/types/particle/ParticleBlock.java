package protocolsupport.protocol.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ParticleBlock extends Particle {

	public ParticleBlock(int id) {
		super(id);
	}

	public ParticleBlock(int id, float offsetX, float offsetY, float offsetZ, float speed, int count, int blockdata) {
		this(id);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = speed;
		this.count = count;
		this.blockdata = blockdata;
	}

	protected int blockdata;

	public int getBlockData() {
		return blockdata;
	}

	@Override
	public void readData(ByteBuf buf) {
		blockdata = VarNumberSerializer.readVarInt(buf);
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockdata);
	}

}
