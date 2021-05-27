package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.typeremapper.particle.LegacyParticleData.LegacyParticleDataTable;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class GenericEntityParticleMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	protected final LegacyParticleDataTable table;
	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> index;

	public GenericEntityParticleMetadataTransformer(LegacyParticleDataTable table, NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> index) {
		this.table = table;
		this.index = index;
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectParticle particleObject = index.getObject(t);
		if (particleObject != null) {
			Particle particle = particleObject.getValue();
			particle = table.get(particle.getClass()).apply(particle);
			if (particle != null) {
				particleObject.setValue(particle);
			} else {
				index.setObject(t, null);
			}
		}
	}

}