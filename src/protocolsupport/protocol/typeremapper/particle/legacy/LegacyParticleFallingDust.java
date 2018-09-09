package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class LegacyParticleFallingDust extends LegacyParticle {

	protected int blockstate;

	public LegacyParticleFallingDust(int id, String name, int blockstate) {
		super(id, name);
		this.blockstate = blockstate;
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockstate);
	}

	@Override
	public int getFirstParameter() {
		return blockstate;
	}

}
