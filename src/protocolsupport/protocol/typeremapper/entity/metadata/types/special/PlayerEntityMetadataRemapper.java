package protocolsupport.protocol.typeremapper.entity.metadata.types.special;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PlayerEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public static final PlayerEntityMetadataRemapper INSTANCE = new PlayerEntityMetadataRemapper();

	protected PlayerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 14), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 13), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 15), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 14), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Player.SCORE, 18), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 15), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 17), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 16), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.LEFT_SHOULDER_ENTITY, 18), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.LEFT_SHOULDER_ENTITY, 17), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 19), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 18), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
				NetworkEntityMetadataObjectIndex.Entity.BASE_FLAGS.getValue(original)
				.ifPresent(baseflags -> entity.getDataCache().setBaseFlags(baseflags.getValue()));
				NetworkEntityMetadataObjectIndex.EntityLiving.HAND_USE.getValue(original)
				.ifPresent(activehandflags -> {
					NetworkEntityDataCache edata = entity.getDataCache();
					edata.setBaseFlag(4, activehandflags.getValue() & 1);
					remapped.add(0, new NetworkEntityMetadataObjectByte((byte) edata.getBaseFlags()));
				});
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

}
