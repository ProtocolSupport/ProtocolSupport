package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleShriek extends NetworkParticle {

	protected int delay;

	public NetworkParticleShriek() {
	}

	public NetworkParticleShriek(float offsetX, float offsetY, float offsetZ, float data, int count, int delay) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	@Override
	public void readData(ByteBuf buf) {
		delay = VarNumberCodec.readVarInt(buf);
	}

}
