package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

//TODO: create construtor that accept target protocol version and locale and add it to remap registry
public class ParticleItem extends Particle {

	public ParticleItem(int pId) {
		super(pId, "minecraft:item");
	}

	protected NetworkItemStack item;
	protected ProtocolVersion version;
	protected String locale;

	public NetworkItemStack getItem() {
		return item;
	}

	@Override
	public void readData(ByteBuf buf) {
		item = ItemStackSerializer.readItemStack(buf, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
	}

	@Override
	public void writeData(ByteBuf buf) {
		ItemStackSerializer.writeItemStack(buf, version, locale, item, true);
	}

}
