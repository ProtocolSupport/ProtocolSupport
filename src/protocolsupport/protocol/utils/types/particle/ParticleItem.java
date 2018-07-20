package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleItem extends Particle {

	@Override
	public String getName() {
		return "minecraft:item";
	}

	@Override
	public void readData(ByteBuf buf) {
		
	}

}
