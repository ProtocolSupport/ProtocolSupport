package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSVarLong;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class TameableEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public TameableEntityMetadataRemapper() {
		addRemap(new PeFlagRemapper(
			NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS, new int[] {1, 2, 3}, new int[] {PeMetaBase.FLAG_SITTING, PeMetaBase.FLAG_ANGRY, PeMetaBase.FLAG_TAMED}
			), ProtocolVersionsHelper.ALL_PE);
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS.getValue(original).ifPresent(byteWatcher -> {
					// If the entity is tamed set owner meta to a dummy entity ID
					if ((byteWatcher.getValue() & 0x04) == 0x04) {
						remapped.put(PeMetaBase.OWNER, new NetworkEntityMetadataObjectSVarLong(0));
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tameable.TAME_FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
