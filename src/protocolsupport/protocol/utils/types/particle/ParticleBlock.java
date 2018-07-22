package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleBlock extends Particle {

	public ParticleBlock(int pId) {
		super(pId, "minecraft:block");
	}

	@Override
	public void readData(ByteBuf buf) {
	}

}
