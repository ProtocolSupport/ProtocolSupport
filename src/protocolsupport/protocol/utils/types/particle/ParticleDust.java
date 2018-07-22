package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleDust extends Particle {

	public ParticleDust(int pId) {
		super(pId, "minecraft:dust");
	}

	@Override
	public void readData(ByteBuf buf) {
		
	}

}
