package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleSculkCharge extends NetworkParticle {

	protected float roll;

	public NetworkParticleSculkCharge() {
	}

	public NetworkParticleSculkCharge(float offsetX, float offsetY, float offsetZ, float data, int count, float roll) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.roll = roll;
	}

	public float getDelay() {
		return roll;
	}

	@Override
	public void readData(ByteBuf buf) {
		roll = buf.readFloat();
	}

}
