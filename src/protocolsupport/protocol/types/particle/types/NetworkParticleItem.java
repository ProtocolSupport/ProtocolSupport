package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleItem extends NetworkParticle {

	protected NetworkItemStack itemstack;

	public NetworkParticleItem() {
		this.itemstack = NetworkItemStack.NULL;
	}

	public NetworkParticleItem(float offsetX, float offsetY, float offsetZ, float data, int count, NetworkItemStack itemstack) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.itemstack = itemstack;
	}

	public NetworkItemStack getItemStack() {
		return itemstack;
	}

	@Override
	public void readData(ByteBuf buf) {
		itemstack = ItemStackSerializer.readItemStack(buf);
	}

}
