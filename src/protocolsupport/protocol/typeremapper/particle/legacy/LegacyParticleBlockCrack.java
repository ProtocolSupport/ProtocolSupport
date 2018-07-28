package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockIdRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;

public class LegacyParticleBlockCrack extends LegacyParticle {

	protected int blockdata;

	public LegacyParticleBlockCrack(int id, String name, ProtocolVersion version, int blockdata) {
		super(id, name);
		int combinedId = BlockIdRemappingHelper.remapToCombinedIdNormal(version, blockdata);
		this.blockdata = PreFlatteningBlockIdData.convertCombinedIdToM12(combinedId);
		this.name += "_" + PreFlatteningBlockIdData.getIdFromCombinedId(combinedId) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(combinedId);
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
