package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleDust extends Particle {

	@Override
	public String getName() {
		return "minecraft:dust";
	}

	@Override
	public void readData(ByteBuf buf) {
		
	}

}
