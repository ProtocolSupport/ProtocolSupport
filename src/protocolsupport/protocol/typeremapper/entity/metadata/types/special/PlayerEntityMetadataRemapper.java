package protocolsupport.protocol.typeremapper.entity.metadata.types.special;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

import java.util.Optional;

public class PlayerEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public PlayerEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				// = PE Nametag =
				// Players has some small edge cases with name tags
				Optional<NetworkEntityMetadataObjectOptionalChat> nameTagWatcher = NetworkEntityMetadataObjectIndex.Entity.NAMETAG.getValue(original);
				Optional<NetworkEntityMetadataObjectBoolean> nameTagVisibilityWatcher = NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE.getValue(original);
				boolean hasCustomNameTagMetaValue = nameTagWatcher.isPresent();
				boolean hasNameTagPersistentMetaValue = nameTagVisibilityWatcher.isPresent();
				if (hasCustomNameTagMetaValue) {
					BaseComponent nameTag = nameTagWatcher.get().getValue();
					if (nameTag != null) // If the nametag
						remapped.put(PeMetaBase.NAMETAG, new NetworkEntityMetadataObjectString(nameTag.toLegacyText()));
					else
						remapped.put(PeMetaBase.NAMETAG, null);
				}
				if (hasNameTagPersistentMetaValue) {
					remapped.put(PeMetaBase.ALWAYS_SHOW_NAMETAG, new NetworkEntityMetadataObjectByte((byte) 1)); // 1 = always visible
				}
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, hasNameTagPersistentMetaValue);
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, true);
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 13), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SCORE, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Player.SCORE, 18), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.LEFT_SHOULDER_ENTITY, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				NetworkEntityMetadataObjectIndex.Entity.FLAGS.getValue(original)
				.ifPresent(baseflags -> entity.getDataCache().setBaseFlags(baseflags.getValue()));
				NetworkEntityMetadataObjectIndex.EntityLiving.HAND_USE.getValue(original)
				.ifPresent(activehandflags -> {
					entity.getDataCache().setBaseFlag(5, activehandflags.getValue());
					remapped.put(0, new NetworkEntityMetadataObjectByte(entity.getDataCache().getBaseFlags()));
				});
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);
	}

}
