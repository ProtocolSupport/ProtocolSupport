package protocolsupport.protocol.types.particle.types;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.codec.VibrationPathCodec;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.VibrationPath;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkParticleVibration extends NetworkParticle {

	protected VibrationPath path;

	public NetworkParticleVibration() {
		path = new VibrationPath(new Any<>(new Position(0, 0, 0), null), 0);
	}

	public NetworkParticleVibration(float offsetX, float offsetY, float offsetZ, float data, int count, VibrationPath path) {
		super(offsetX, offsetY, offsetZ, data, count);
		this.path = path;
	}

	public VibrationPath getPath() {
		return path;
	}

	@Override
	public void readData(ByteBuf from) {
		path = VibrationPathCodec.readVibrationPath(from);
	}

}
