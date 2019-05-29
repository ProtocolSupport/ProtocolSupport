package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class LegacyParticleIconCrack extends LegacyParticle {

	protected NetworkItemStack item;

	public LegacyParticleIconCrack(int id, String name, float offsetX, float offsetY, float offsetZ, float data, int count, NetworkItemStack item) {
		super(id, name, offsetX, offsetY, offsetZ, data, count);
		this.item = item;
		this.name += "_" + item.getTypeId() + "_" + item.getLegacyData();
	}

	@Override
	public void writeData(ByteBuf buf) {
		VarNumberSerializer.writeVarInt(buf, item.getTypeId());
		VarNumberSerializer.writeVarInt(buf, item.getLegacyData());
	}

	@Override
	public int getFirstParameter() {
		return item.getTypeId();
	}

	@Override
	public int getSecondParameter() {
		return item.getLegacyData();
	}

}
