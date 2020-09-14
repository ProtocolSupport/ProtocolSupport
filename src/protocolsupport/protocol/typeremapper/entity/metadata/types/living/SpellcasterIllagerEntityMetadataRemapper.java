package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SpellcasterIllagerEntityMetadataRemapper extends IllagerEntityMetadataRemapper {

	public static final SpellcasterIllagerEntityMetadataRemapper INSTANCE = new SpellcasterIllagerEntityMetadataRemapper();

	protected SpellcasterIllagerEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.SpellcasterIllager.SPELL, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.SpellcasterIllager.SPELL, 15), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.SpellcasterIllager.SPELL, 13), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
