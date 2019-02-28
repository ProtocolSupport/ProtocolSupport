package protocolsupport.protocol.typeremapper.entity.metadata.types.special;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagAdder;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.*;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityDataCache;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public PlayerEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				// = PE Nametag =
				// Players has some small edge cases with name tags
				Optional<DataWatcherObjectOptionalChat> nameTagWatcher = DataWatcherObjectIndex.Entity.NAMETAG.getValue(original);
				Optional<DataWatcherObjectBoolean> nameTagVisibilityWatcher = DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE.getValue(original);

				boolean hasCustomNameTagMetaValue = nameTagWatcher.isPresent();
				boolean hasNameTagPersistentMetaValue = nameTagVisibilityWatcher.isPresent();

				if (hasCustomNameTagMetaValue) {
					BaseComponent nameTag = nameTagWatcher.get().getValue();
					if (nameTag != null) // If the nametag
						remapped.put(PeMetaBase.NAMETAG, new DataWatcherObjectString(nameTag.toLegacyText()));
					else
						remapped.put(PeMetaBase.NAMETAG, null); // Remove old tag (set by the BaseEntityMetadataRemapper class)
				}

				if (hasNameTagPersistentMetaValue) {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, true); // ALWAYS show nametag if the meta value is set (no matter if it is true or false)
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, true);
					remapped.put(PeMetaBase.ALWAYS_SHOW_NAMETAG, new DataWatcherObjectByte((byte) 1)); // 1 = always visible
				} else { // If the name tag persistent meta value ISN'T SET, it means that the name tag should be hidden (Citizens does that to hide nameplates)
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, false); // ALWAYS show nametag if the meta value is set (no matter if it is true or false)
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, false);
				}
			}
		}, ProtocolVersionsHelper.ALL_PE);

		addRemap(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 12),  ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 11),  ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Player.SCORE, 18),  ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.RANGE__1_12__1_13_2);
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Entity.FLAGS.getValue(original)
					.ifPresent(baseflags -> entity.getDataCache().setBaseFlags(baseflags.getValue()));
				DataWatcherObjectIndex.EntityLiving.HAND_USE.getValue(original)
					.ifPresent(activehandflags -> {
						entity.getDataCache().setBaseFlag(5, activehandflags.getValue());
						remapped.put(0, new DataWatcherObjectByte(entity.getDataCache().getBaseFlags()));
					});
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);
	}

}
