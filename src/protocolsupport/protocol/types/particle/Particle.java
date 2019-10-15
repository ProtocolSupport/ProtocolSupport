package protocolsupport.protocol.types.particle;

import io.netty.buffer.ByteBuf;

public abstract class Particle {

	protected float offsetX;
	protected float offsetY;
	protected float offsetZ;
	protected float data;
	protected int count;

	protected Particle() {
	}

	protected Particle(float offsetX, float offsetY, float offsetZ, float data, int count) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = data;
		this.count = count;
	}

	public void read(ByteBuf buf) {
		offsetX = buf.readFloat();
		offsetY = buf.readFloat();
		offsetZ = buf.readFloat();
		data = buf.readFloat();
		count = buf.readInt();
		readData(buf);
	}

	public void readData(ByteBuf from) {
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public float getOffsetZ() {
		return offsetZ;
	}

	public float getData() {
		return data;
	}

	public int getCount() {
		return count;
	}

}
