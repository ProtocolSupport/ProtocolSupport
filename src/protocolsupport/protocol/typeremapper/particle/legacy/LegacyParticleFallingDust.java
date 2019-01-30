package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;

public class LegacyParticleFallingDust extends LegacyParticle {

	protected int blockdata;

	public LegacyParticleFallingDust(int id, String name, float offsetX, float offsetY, float offsetZ, float speed, int data, int blockdata) {
		super(id, name, offsetX, offsetY, offsetZ, speed, data);
		this.blockdata = PreFlatteningBlockIdData.convertCombinedIdToM12(blockdata);
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockdata);
	}

	@Override
	public int getFirstParameter() {
		return blockdata;
	}

}
