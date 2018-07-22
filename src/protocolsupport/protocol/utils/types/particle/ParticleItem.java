package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

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

	public void setItem(NetworkItemStack item) {
		this.item = item;
	}

	@Override
	public void readData(ByteBuf buf) {
		item = ItemStackSerializer.readItemStack(buf, ProtocolVersion.getLatest(ProtocolType.PC), I18NData.DEFAULT_LOCALE, false);
	}

	@Override
	public void remap(ProtocolVersion version, String locale) {
		item = ItemStackRemapper.remapToClient(version, locale, item);
	}

	@Override
	public void writeData(ByteBuf buf) {
		ItemStackSerializer.writeItemStack(buf, version, locale, item, true);
	}

}
