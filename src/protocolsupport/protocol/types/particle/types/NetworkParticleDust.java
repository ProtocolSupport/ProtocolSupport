package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleDust extends NetworkParticle {

	protected float red;
	protected float green;
	protected float blue;
	protected float scale;

	public NetworkParticleDust() {
	}

	public NetworkParticleDust(
		float offsetX, float offsetY, float offsetZ, float data, int count,
		float red, float green, float blue, float scale
	) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.scale = scale;
	}

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

}
