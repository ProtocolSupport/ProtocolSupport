package protocolsupport.protocol.types.particle;

import io.netty.buffer.ByteBuf;

public class Particle {

	protected final int id;

	protected float offsetX;
	protected float offsetY;
	protected float offsetZ;
	protected float data;
	protected int count;

	public Particle(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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

	public void writeData(ByteBuf buf) {
	}

}
