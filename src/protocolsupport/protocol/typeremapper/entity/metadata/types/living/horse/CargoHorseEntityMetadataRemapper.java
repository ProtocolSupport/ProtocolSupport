package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class CargoHorseEntityMetadataRemapper extends BaseHorseEntityMetadataRemapper {

	public static final CargoHorseEntityMetadataRemapper INSTANCE = new CargoHorseEntityMetadataRemapper();

	public CargoHorseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.CargoHorse.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
