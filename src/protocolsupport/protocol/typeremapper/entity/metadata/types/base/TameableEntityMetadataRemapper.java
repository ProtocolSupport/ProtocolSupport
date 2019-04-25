package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class TameableEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public TameableEntityMetadataRemapper() {
		addRemap(new PeFlagRemapper(
				DataWatcherObjectIndex.Tameable.TAME_FLAGS, new int[] {1, 2, 3}, new int[] {PeMetaBase.FLAG_SITTING, PeMetaBase.FLAG_ANGRY, PeMetaBase.FLAG_TAMED}
			), ProtocolVersionsHelper.ALL_PE);
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Tameable.TAME_FLAGS.getValue(original).ifPresent(byteWatcher -> {
					// If the entity is tamed set owner meta to a dummy entity ID
					if ((byteWatcher.getValue() & 0x04) == 0x04) {
						remapped.put(PeMetaBase.OWNER, new DataWatcherObjectSVarLong(0));
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
