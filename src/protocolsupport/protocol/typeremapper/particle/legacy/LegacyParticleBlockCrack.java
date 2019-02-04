package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;

public class LegacyParticleBlockCrack extends LegacyParticle {

	protected int blockdata;

	public LegacyParticleBlockCrack(int id, String name, float offsetX, float offsetY, float offsetZ, float data, int count, int blockdata) {
		super(id, name, offsetX, offsetY, offsetZ, data, count);
		this.blockdata = PreFlatteningBlockIdData.convertCombinedIdToM12(blockdata);
		this.name += "_" + PreFlatteningBlockIdData.getIdFromCombinedId(blockdata) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(blockdata);
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
