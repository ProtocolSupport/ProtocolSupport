package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleFallingDust extends Particle {

	@Override
	public String getName() {
		return "minecraft:falling_dust";
	}

	@Override
	public void readData(ByteBuf buf) {
		
	}

}
