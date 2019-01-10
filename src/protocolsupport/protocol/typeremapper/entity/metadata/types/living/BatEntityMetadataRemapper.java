package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class BatEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public BatEntityMetadataRemapper() {
		addRemap(new PeFlagRemapper(DataWatcherObjectIndex.Bat.HANGING,
				new int[] {1, 1}, new int[] {PeMetaBase.FLAG_RESTING, -PeMetaBase.FLAG_GRAVITY}), //If the bat is hanging, remove it's gravity to prevent it from falling.
		ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
