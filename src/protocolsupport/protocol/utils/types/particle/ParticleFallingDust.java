package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ParticleFallingDust extends Particle {

	public ParticleFallingDust(int pId) {
		super(pId, "minecraft:falling_dust");
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
