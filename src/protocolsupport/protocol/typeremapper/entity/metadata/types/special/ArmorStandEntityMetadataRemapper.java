package protocolsupport.protocol.typeremapper.entity.metadata.types.special;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ArmorStandEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public static final ArmorStandEntityMetadataRemapper INSTANCE = new ArmorStandEntityMetadataRemapper();

	public ArmorStandEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.FLAGS, 13), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.FLAGS, 10), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.HEAD_ROT, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.HEAD_ROT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.HEAD_ROT, 11), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.BODY_ROT, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.BODY_ROT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.BODY_ROT, 12), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_ARM_ROT, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_ARM_ROT, 13), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_ARM_ROT, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_ARM_ROT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_LEG_ROT, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.LEFT_LEG_ROT, 15), ProtocolVersionsHelper.RANGE__1_8__1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_LEG_ROT, 19), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_LEG_ROT, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ArmorStand.RIGHT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_8__1_9);
	}

}
