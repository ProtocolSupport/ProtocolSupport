package protocolsupport.protocol.typeremapper.particle.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class LegacyParticleIconCrack extends LegacyParticle {

	protected NetworkItemStack item;
	protected ProtocolVersion version;
	protected String locale;

	public LegacyParticleIconCrack(int id, String name, ProtocolVersion version, NetworkItemStack item) {
		super(id, name);
		this.item = ItemStackRemapper.remapToClient(version, locale, item);
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
		return item.getAmount();
	}

}
