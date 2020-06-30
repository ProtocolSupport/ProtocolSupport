package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class StriderEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public static final StriderEntityMetadataRemapper INSTANCE = new StriderEntityMetadataRemapper();

	protected StriderEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Strider.BOOST_TIME, 16), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Strider.DISPLAY_NAMETAG, 17), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Strider.HAS_SADDLE, 18), ProtocolVersionsHelper.UP_1_16);
	}

}
