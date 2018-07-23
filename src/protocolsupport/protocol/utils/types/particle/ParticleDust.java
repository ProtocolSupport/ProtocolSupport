package protocolsupport.protocol.utils.types.particle;

import io.netty.buffer.ByteBuf;

public class ParticleDust extends Particle {

	public ParticleDust(int pId) {
		super(pId, "minecraft:dust");
	}

	protected float red;
	protected float green;
	protected float blue;
	protected float scale;

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
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
