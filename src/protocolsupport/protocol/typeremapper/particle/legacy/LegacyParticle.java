package protocolsupport.protocol.typeremapper.particle.legacy;

import protocolsupport.protocol.types.particle.Particle;

public class LegacyParticle extends Particle {

	protected String name;

	public LegacyParticle(int id, String name, float offsetX, float offsetY, float offsetZ, float data, int count) {
		super(id);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = data;
		this.count = count;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getFirstParameter() {
		return 0;
	}

	public int getSecondParameter() {
		return 0;
	}

}
