package protocolsupport.protocol.typeremapper.particle.legacy;

import protocolsupport.protocol.utils.types.particle.Particle;

public class LegacyParticle extends Particle {

	public LegacyParticle(int id, String name) {
		super(id, name);
	}

	public int getFirstParameter() {
		return 0;
	}

	public int getSecondParameter() {
		return 0;
	}

}
