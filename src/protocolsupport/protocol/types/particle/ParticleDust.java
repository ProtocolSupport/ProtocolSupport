package protocolsupport.protocol.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleDust extends Particle {

	public ParticleDust(int pId) {
		super(pId);
	}

	protected float red;
	protected float green;
	protected float blue;
	protected float scale;

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public float getScale() {
		return scale;
	}

	@Override
	public void readData(ByteBuf buf) {
		red = buf.readFloat();
		green = buf.readFloat();
		blue = buf.readFloat();
		scale = buf.readFloat();
	}

	@Override
	public void writeData(ByteBuf buf) {
		buf.writeFloat(red);
		buf.writeFloat(green);
		buf.writeFloat(blue);
		buf.writeFloat(scale);
	}

}
