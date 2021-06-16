package particle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleIntIdRegistryDataSerializer;
import protocolsupport.protocol.typeremapper.particle.PreFlatteningNetworkParticleStringIdRegistryDataSerializer;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import zinit.PlatformInit;

class NetworkParticleTransformTests extends PlatformInit {

	@Test
	void testMappingRegistries() {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
			NetworkParticleLegacyDataTable legacyDataTable = NetworkParticleLegacyData.REGISTRY.getTable(version);
			for (NetworkParticle particle : NetworkParticleRegistry.getAll()) {
				NetworkParticle mappedParticle = particle;

				mappedParticle = legacyDataTable.get(mappedParticle.getClass()).apply(mappedParticle);
				if (mappedParticle == null) {
					continue;
				}

				NetworkParticle fParticle = mappedParticle;
				if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13)) {
					Assertions.assertDoesNotThrow(
						() -> FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version).get(NetworkParticleRegistry.getId(fParticle)),
						"NetworkParticleFlatteningId for ProtocolVersion " + version + " and NetworkParticle " + particle.getClass().getSimpleName()
					);
				} else if (version.isBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_12_2)) {
					Assertions.assertDoesNotThrow(
						() -> PreFlatteningNetworkParticleIntIdRegistryDataSerializer.getId(fParticle),
						"NetworkParticlePreFlatteningIntId for ProtocolVersion " + version + " and NetworkParticle " + particle.getClass().getSimpleName()
					);
				} else {
					Assertions.assertDoesNotThrow(
						() -> PreFlatteningNetworkParticleStringIdRegistryDataSerializer.getId(fParticle),
						"NetworkParticlePreFlatteningStringId for ProtocolVersion " + version + " and NetworkParticle " + particle.getClass().getSimpleName()
					);
				}
			}
		}
	}

}
