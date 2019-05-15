package protocolsupport.protocol.types.particle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public class ParticleItem extends Particle {

	public ParticleItem(int pId) {
		super(pId);
	}

	protected ProtocolVersion version;
	protected String locale;

	public ParticleItem(int pId, float offsetX, float offsetY, float offsetZ, float speed, int count, ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		this(pId);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = speed;
		this.count = count;
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
		itemstack = ItemStackSerializer.readItemStack(buf);
	}

	@Override
	public void writeData(ByteBuf buf) {
		ItemStackSerializer.writeItemStack(buf, version, locale, itemstack);
	}

}
