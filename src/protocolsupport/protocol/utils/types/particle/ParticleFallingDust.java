package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ParticleFallingDust extends Particle {

	public ParticleFallingDust(int pId) {
		super(pId);
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
