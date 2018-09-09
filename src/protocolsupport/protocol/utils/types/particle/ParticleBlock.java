package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockId;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;

public class ParticleBlock extends Particle {

	public ParticleBlock(int pId) {
		super(pId, "minecraft:block");
	}

	public ParticleBlock(int pId, ProtocolVersion version, int blockdata) {
		this(pId);
		this.blockdata = FlatteningBlockId.REGISTRY.getTable(version).getRemap(LegacyBlockData.REGISTRY.getTable(version).getRemap(blockdata));
	}

	protected int blockdata;

	public int getBlockData() {
		return blockdata;
	}

	@Override
	public void readData(ByteBuf buf) {
		blockdata = VarNumberSerializer.readVarInt(buf);
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, blockdata);
	}

}
