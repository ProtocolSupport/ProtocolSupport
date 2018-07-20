package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleBlock extends Particle {

	@Override
	public String getName() {
		return "minecraft:block";
	}

	@Override
	public void readData(ByteBuf buf) {
	}

}
