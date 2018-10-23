package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class LivingEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public LivingEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 10, 64), ProtocolVersionsHelper.RANGE__1_6__1_7);
		addRemap(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 5, 64), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 11), ProtocolVersionsHelper.RANGE__1_6__1_7);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 6), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 5), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 6), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1));

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.RANGE__1_6__1_8);
		addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.BEFORE_1_6);
	}

}
