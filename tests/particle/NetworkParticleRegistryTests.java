package particle;

import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.protocol.types.particle.NetworkParticleRegistry;
import protocolsupport.protocol.utils.minecraftdata.MinecraftParticleData;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;
import zinit.PlatformInit;

class NetworkParticleRegistryTests extends PlatformInit {

	@Test
	void testFilled() {
		PlatformUtils utils = ServerPlatform.get().getMiscUtils();
		for (Particle particle : Particle.values()) {
			if (particle.name().startsWith("LEGACY_")) {
				continue;
			}
			NamespacedKey key = utils.getParticleKey(particle);
			if (key == null) {
				continue;
			}
			Assertions.assertDoesNotThrow(() -> NetworkParticleRegistry.fromId(MinecraftParticleData.getIdByName(key.toString())), "NetworkParticle for Bukkit Particle " + particle);
		}
	}

}
