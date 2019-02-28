package protocolsupport.protocol.typeremapper.entity.metadata.types.special;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils;

import java.util.Optional;

public class ArmorStandEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public static final ArmorStandEntityMetadataRemapper INSTANCE = new ArmorStandEntityMetadataRemapper();

	public ArmorStandEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 10), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 11), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 12), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 13), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 15), ProtocolVersionsHelper.RANGE__1_8__1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_8__1_9);
	}

}
