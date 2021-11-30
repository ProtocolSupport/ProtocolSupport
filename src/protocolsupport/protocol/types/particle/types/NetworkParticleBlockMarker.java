package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleBlockMarker extends NetworkParticle {

	protected int blockdata;

	public NetworkParticleBlockMarker() {
	}

	public NetworkParticleBlockMarker(float offsetX, float offsetY, float offsetZ, float data, int count, int blockdata) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.blockdata = blockdata;
	}

	public int getBlockData() {
		return blockdata;
	}

	@Override
	public void readData(ByteBuf buf) {
		blockdata = VarNumberCodec.readVarInt(buf);
	}

}
