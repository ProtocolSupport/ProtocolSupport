package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;

public class LegacyParticleBlockCrack extends LegacyParticle {

	protected int blockstate;

	public LegacyParticleBlockCrack(int id, String name, ProtocolVersion version, int blockstate) {
		super(id, name);
		int combinedId = PreFlatteningBlockIdData.getLegacyCombinedId(LegacyBlockData.REGISTRY.getTable(version).getRemap(blockstate));
		this.blockstate = PreFlatteningBlockIdData.getLegacyObjDataFromLegacyBlockState(combinedId);
		this.name += "_" + PreFlatteningBlockIdData.getIdFromLegacyCombinedId(combinedId) + "_" + PreFlatteningBlockIdData.getDataFromLegacyCombinedId(combinedId);
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
