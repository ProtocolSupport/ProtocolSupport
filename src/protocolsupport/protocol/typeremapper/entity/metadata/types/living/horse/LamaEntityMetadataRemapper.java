package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LamaEntityMetadataRemapper extends CargoHorseEntityMetadataRemapper {

	public static final LamaEntityMetadataRemapper INSTANCE = new LamaEntityMetadataRemapper();

	public LamaEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
