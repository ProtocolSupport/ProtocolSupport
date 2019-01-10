package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;

public class SheepEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public SheepEntityMetadataRemapper() {
		addRemap(new IndexValueRemapper<DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, PeMetaBase.COLOR) {
			@Override
			public DataWatcherObjectByte remapValue(DataWatcherObjectByte object) {
				return new DataWatcherObjectByte((byte) (object.getValue() & 0x0F));
			}
		}, ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeFlagRemapper(DataWatcherObjectIndex.Sheep.FLAGS,
			new int[] {5}, new int[] {PeMetaBase.FLAG_SHEARED}),
		ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
