package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
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
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3fLe;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BaseEntityMetadataRemapper extends EntityMetadataRemapper {

	public static final BaseEntityMetadataRemapper INSTANCE = new BaseEntityMetadataRemapper();

	class PEDataWatcherRemapper extends DataWatcherObjectRemapper {
		final Function<Integer, Integer> idMap;
		final ProtocolVersion version;

		PEDataWatcherRemapper(ProtocolVersion version) {
			this.version = version;
			this.idMap = id -> {
				if (id >= PeMetaBase.BOUNDINGBOX_WIDTH && id < PeMetaBase.ALWAYS_SHOW_NAMETAG
						&& version.equals(ProtocolVersion.MINECRAFT_PE_1_11)) {
					return id + 1;
				}
				return id;
			};
		}

		@Override
		public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
			final BiConsumer<Integer, DataWatcherObject<?>> mapPut = (k, v) -> remapped.put(idMap.apply(k), v);

			NetworkEntityDataCache data = entity.getDataCache();
			float entitySize = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType()) * data.getSizeModifier();
			// = PE Lead =
			//Leashing is set in Entity Leash.
			entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_LEASHED, data.getAttachedId() != -1);
			mapPut.accept(PeMetaBase.LEADHOLDER, new DataWatcherObjectSVarLong(data.getAttachedId()));
			// = PE Nametag =
			//Doing this for players makes nametags behave weird or only when close.
			if (entity.getType() != NetworkEntityType.PLAYER) {
				Optional<DataWatcherObjectOptionalChat> nameTagWatcher = DataWatcherObjectIndex.Entity.NAMETAG.getValue(original);
				boolean doNameTag = nameTagWatcher.isPresent();
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, doNameTag);
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, doNameTag); // does nothing?
				if (doNameTag) {
					BaseComponent nameTag = nameTagWatcher.get().getValue();
					mapPut.accept(PeMetaBase.NAMETAG, new DataWatcherObjectString(nameTag != null ? nameTag.toLegacyText() : ""));
					mapPut.accept(PeMetaBase.ALWAYS_SHOW_NAMETAG, new DataWatcherObjectByte((byte) 1)); // 1 for always display, 0 for display when look
				}
			}
			// = PE Riding =
			entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_COLLIDE, !data.isRiding());
			if (data.isRiding()) {
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, true);
				mapPut.accept(PeMetaBase.RIDER_POSITION, new DataWatcherObjectVector3fLe(data.getRiderPosition()));
				mapPut.accept(PeMetaBase.RIDER_LOCK, new DataWatcherObjectByte((byte) ((data.getRotationLock() != null) ? 1 : 0)));
				if (data.getRotationLock() != null) {
					mapPut.accept(PeMetaBase.RIDER_MAX_ROTATION, new DataWatcherObjectFloatLe(data.getRotationLock()));
					mapPut.accept(PeMetaBase.RIDER_MIN_ROTATION, new DataWatcherObjectFloatLe(-data.getRotationLock()));
				}
			} else {
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, false);
			}
			// = PE Air =
			AtomicInteger air = new AtomicInteger(0);
			DataWatcherObjectIndex.Entity.AIR.getValue(original).ifPresent(airWatcher -> {
				air.set(airWatcher.getValue() >= 300 ? 0 : airWatcher.getValue());
			});
			mapPut.accept(PeMetaBase.AIR, new DataWatcherObjectShortLe(air.get()));
			mapPut.accept(PeMetaBase.MAX_AIR, new DataWatcherObjectShortLe(300));
			// = PE Bounding Box =
			PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
			if (pocketdata == null || pocketdata.getBoundingBox() == null) {
				ProtocolSupport.logWarning("PE BoundingBox missing for entity: " + entity.getType());
			} else {
				mapPut.accept(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
				mapPut.accept(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
			}
			// = PE Size =
			mapPut.accept(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize));
			// = PE Interaction =
			String interactText = PEMetaProviderSPI.getProvider().getUseText(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
			if (interactText != null) {
				if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_10)) {
					mapPut.accept(PeMetaBase.BUTTON_TEXT_V1, new DataWatcherObjectString(interactText));
				} else {
					mapPut.accept(PeMetaBase.BUTTON_TEXT_V2, new DataWatcherObjectString(interactText));
				}
			}
		}
	}

	public BaseEntityMetadataRemapper() {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL_PE) {
			addRemap(new PEDataWatcherRemapper(version), version);
		}
		addRemap(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_GRAVITY}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeFlagRemapper(DataWatcherObjectIndex.Entity.FLAGS,
			new int[] {1, 2, 4, 5, 6, 8}, new int[] {
				PeMetaBase.FLAG_ON_FIRE,
				PeMetaBase.FLAG_SNEAKING,
				PeMetaBase.FLAG_SPRINTING,
				PeMetaBase.FLAG_SWIMMING,
				PeMetaBase.FLAG_INVISIBLE,
				PeMetaBase.FLAG_GLIDING}
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
