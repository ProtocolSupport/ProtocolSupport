package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public class ParticleItem extends Particle {

	public ParticleItem(int pId) {
		super(pId, "minecraft:item");
	}

	protected ProtocolVersion version;
	protected String locale;

	public ParticleItem(int pId, ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		this(pId);
		this.version = version;
		this.locale = locale;
		this.itemstack = itemstack;
	}

	protected NetworkItemStack itemstack;

	public NetworkItemStack getItemStack() {
		return itemstack;
	}

	@Override
	public void readData(ByteBuf buf) {
		itemstack = ItemStackSerializer.readItemStack(buf, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
	}

	@Override
	public void writeData(ByteBuf buf) {
		ItemStackSerializer.writeItemStack(buf, version, locale, itemstack, true);
	}

}
