package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagAdder;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.*;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityDataCache;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BaseEntityMetadataRemapper extends EntityMetadataRemapper {

	public static final BaseEntityMetadataRemapper INSTANCE = new BaseEntityMetadataRemapper();

	public BaseEntityMetadataRemapper() {
		addRemap(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				NetworkEntityDataCache data = entity.getDataCache();
				float entitySize = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType()) * data.getSizeModifier();
				// = PE Lead =
				//Leashing is set in Entity Leash.
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_LEASHED, data.getAttachedId() != -1);
				remapped.put(PeMetaBase.LEADHOLDER, new DataWatcherObjectSVarLong(data.getAttachedId()));

				// = PE Nametag =
				Optional<DataWatcherObjectOptionalChat> nameTagWatcher = DataWatcherObjectIndex.Entity.NAMETAG.getValue(original);
				Optional<DataWatcherObjectBoolean> nameTagVisibilityWatcher = DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE.getValue(original);

				boolean hasCustomNameTagMetaValue = nameTagWatcher.isPresent();
				boolean hasNameTagPersistentMetaValue = nameTagVisibilityWatcher.isPresent();

				if (hasCustomNameTagMetaValue) {
					BaseComponent nameTag = nameTagWatcher.get().getValue();
					remapped.put(PeMetaBase.NAMETAG, new DataWatcherObjectString(nameTag != null ? nameTag.toLegacyText() : ""));
				}

				if (hasNameTagPersistentMetaValue) {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, nameTagVisibilityWatcher.get().getValue());
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, nameTagVisibilityWatcher.get().getValue()); // used by armor stands (holograms)
					remapped.put(PeMetaBase.ALWAYS_SHOW_NAMETAG, new DataWatcherObjectByte((byte) (nameTagVisibilityWatcher.get().getValue() ? 1 : 0))); // 1 = always display, 0 = display when looking at the entity
				}

				// = PE Riding =
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_COLLIDE, !data.isRiding());
				if (data.isRiding()) {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, true);
					remapped.put(PeMetaBase.RIDER_POSITION, new DataWatcherObjectVector3fLe(data.getRiderPosition()));
					remapped.put(PeMetaBase.RIDER_LOCK, new DataWatcherObjectByte((byte) ((data.getRotationLock() != null) ? 1 : 0)));
					if (data.getRotationLock() != null) {
						remapped.put(PeMetaBase.RIDER_MAX_ROTATION, new DataWatcherObjectFloatLe(data.getRotationLock()));
						remapped.put(PeMetaBase.RIDER_MIN_ROTATION, new DataWatcherObjectFloatLe(-data.getRotationLock()));
					}
				} else {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, false);
				}
				// = PE Air =
				AtomicInteger air = new AtomicInteger(0);
				DataWatcherObjectIndex.Entity.AIR.getValue(original).ifPresent(airWatcher -> {
					air.set(airWatcher.getValue() >= 300 ? 0 : airWatcher.getValue());
				});
				remapped.put(PeMetaBase.AIR, new DataWatcherObjectShortLe(air.get()));
				remapped.put(PeMetaBase.MAX_AIR, new DataWatcherObjectShortLe(300));
				// = PE Bounding Box =
				PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
				if (pocketdata == null) {
					ProtocolSupport.logWarning("PE BoundingBox missing for entity: " + entity.getType());
				} else {
					if (pocketdata.getBoundingBox() != null) {
						remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
						remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
					}
				}
				// = PE Size =
				remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize));
				// = PE Interaction =
				String interactText = PEMetaProviderSPI.getProvider().getUseText(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
				if (interactText != null) {
					remapped.put(PeMetaBase.BUTTON_TEXT, new DataWatcherObjectString(interactText));
				}
			}
		}, ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_GRAVITY}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeFlagRemapper(DataWatcherObjectIndex.Entity.FLAGS,
			new int[] {1, 2, 4, 6, 8}, new int[] {PeMetaBase.FLAG_ON_FIRE, PeMetaBase.FLAG_SNEAKING, PeMetaBase.FLAG_SPRINTING, PeMetaBase.FLAG_INVISIBLE, PeMetaBase.FLAG_GLIDING}
		), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.SILENT, PeMetaBase.FLAG_SILENT), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.NO_GRAVITY, -PeMetaBase.FLAG_GRAVITY), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.RANGE__1_9__1_13_2);
		addRemap(new IndexValueRemapperNumberToShort(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.RANGE__1_9__1_13_2);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.RANGE__1_9__1_13_2);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
	}

}
