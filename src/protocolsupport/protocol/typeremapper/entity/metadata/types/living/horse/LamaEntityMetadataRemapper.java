package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class LamaEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LamaEntityMetadataRemapper INSTANCE = new LamaEntityMetadataRemapper();

	public LamaEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
