package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ParticleBlock extends Particle {

	public ParticleBlock(int pId) {
		super(pId, "minecraft:block");
	}

	protected int blockstate;

	public int getBlockstate() {
		return blockstate;
	}

	public void setBlockstate(int blockstate) {
		this.blockstate = blockstate;
	}

	@Override
	public void readData(ByteBuf buf) {
		blockstate = VarNumberSerializer.readVarInt(buf);
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockstate);
	}

}
