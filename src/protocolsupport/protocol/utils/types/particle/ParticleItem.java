package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleItem extends Particle {

	public ParticleItem(int pId) {
		super(pId, "minecraft:item");
	}

	@Override
	public void readData(ByteBuf buf) {
		
	}

}
