package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;

public class NetworkParticleDustTransition extends NetworkParticleDust {

	protected float targetRed;
	protected float targetGreen;
	protected float targetBlue;

	public NetworkParticleDustTransition() {
	}

	public NetworkParticleDustTransition(
		float offsetX, float offsetY, float offsetZ, float data, int count,
		float red, float green, float blue, float scale,
		float targetRed, float targetGreen, float targetBlue
	) {
		super(offsetX, offsetY, offsetZ, data, count, red, green, blue, scale);
		this.targetRed = targetRed;
		this.targetGreen = targetGreen;
		this.targetBlue = targetBlue;
	}

	public float getTargetRed() {
		return targetRed;
	}

	public float getTargetGreen() {
		return targetGreen;
	}

	public float getTargetBlue() {
		return targetBlue;
	}

	@Override
	public void readData(ByteBuf buf) {
		super.readData(buf);
		targetRed = buf.readFloat();
		targetGreen = buf.readFloat();
		targetBlue = buf.readFloat();
	}

}
