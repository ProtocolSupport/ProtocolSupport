package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LivingEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final LivingEntityMetadataRemapper INSTANCE = new LivingEntityMetadataRemapper();

	protected LivingEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 10), ProtocolVersionsHelper.ALL_1_7);
		addRemap(new IndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 10, 64), ProtocolVersionsHelper.ALL_1_6);
		addRemap(new IndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 5, 64), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 11), ProtocolVersionsHelper.RANGE__1_6__1_7);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 6), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HAND_USE, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HAND_USE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HAND_USE, 5), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HEALTH, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HEALTH, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.HEALTH, 6), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_COLOR, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_AMBIENT, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.ARROWS_IN, 11), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.ABSORBTION_HEALTH, 12), ProtocolVersionsHelper.UP_1_15);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION, 13), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION, 12), ProtocolVersionsHelper.ALL_1_14);
	}

}
