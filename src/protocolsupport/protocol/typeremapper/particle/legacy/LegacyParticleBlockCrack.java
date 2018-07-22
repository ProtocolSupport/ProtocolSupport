package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.LegacyBlockId;
import protocolsupport.protocol.utils.types.particle.Particle;

public class LegacyParticleBlockCrack extends Particle {

	protected int blockstate;

	public LegacyParticleBlockCrack(int id, String name, int blockstate) {
		super(id, name);
		this.blockstate = blockstate;
	}

	@Override
	public void remap(Connection connection, NetworkDataCache cache) {
		blockstate = LegacyBlockId.getLegacyObjDataFromLegacyBlockState(LegacyBlockId.getLegacyCombinedId(LegacyBlockData.REGISTRY.getTable(connection.getVersion()).getRemap(blockstate)));
		name += "_" + blockstate;
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockstate);
	}

}
