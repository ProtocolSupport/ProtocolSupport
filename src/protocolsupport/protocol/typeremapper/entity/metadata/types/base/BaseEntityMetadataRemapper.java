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
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagAdder;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloatLe;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSVarLong;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataShortLe;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3fLe;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class BaseEntityMetadataRemapper extends EntityMetadataRemapper {

	public static final BaseEntityMetadataRemapper INSTANCE = new BaseEntityMetadataRemapper();

	class PEDataWatcherRemapper extends NetworkEntityMetadataObjectRemapper {
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
		public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
			final BiConsumer<Integer, NetworkEntityMetadataObject<?>> mapPut = (k, v) -> remapped.put(idMap.apply(k), v);

			NetworkEntityDataCache data = entity.getDataCache();
			float entitySize = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType()) * data.getSizeModifier();
			// = PE Lead =
			//Leashing is set in Entity Leash.
			entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_LEASHED, data.getAttachedId() != -1);
			mapPut.accept(PeMetaBase.LEADHOLDER, new NetworkEntityMetadataObjectSVarLong(data.getAttachedId()));
			// = PE Nametag =
			//Doing this for players makes nametags behave weird or only when close.
			if (entity.getType() != NetworkEntityType.PLAYER) {
				Optional<NetworkEntityMetadataObjectOptionalChat> nameTagWatcher = NetworkEntityMetadataObjectIndex.Entity.NAMETAG.getValue(original);
				boolean doNameTag = nameTagWatcher.isPresent();
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, doNameTag);
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, doNameTag); // does nothing?
				if (doNameTag) {
					BaseComponent nameTag = nameTagWatcher.get().getValue();
					mapPut.accept(PeMetaBase.NAMETAG, new NetworkEntityMetadataObjectString(nameTag != null ? nameTag.toLegacyText() : ""));
					mapPut.accept(PeMetaBase.ALWAYS_SHOW_NAMETAG, new NetworkEntityMetadataObjectByte((byte) 1)); // 1 for always display, 0 for display when look
				}
			}
			// = PE Riding =
			entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_COLLIDE, !data.isRiding());
			if (data.isRiding()) {
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, true);
				mapPut.accept(PeMetaBase.RIDER_POSITION, new NetworkEntityMetadataObjectVector3fLe(data.getRiderPosition()));
				mapPut.accept(PeMetaBase.RIDER_LOCK, new NetworkEntityMetadataObjectByte((byte) ((data.getRotationLock() != null) ? 1 : 0)));
				if (data.getRotationLock() != null) {
					mapPut.accept(PeMetaBase.RIDER_MAX_ROTATION, new NetworkEntityMetadataObjectFloatLe(data.getRotationLock()));
					mapPut.accept(PeMetaBase.RIDER_MIN_ROTATION, new NetworkEntityMetadataObjectFloatLe(-data.getRotationLock()));
				}
			} else {
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, false);
			}
			// = PE Air =
			AtomicInteger air = new AtomicInteger(0);
			NetworkEntityMetadataObjectIndex.Entity.AIR.getValue(original).ifPresent(airWatcher -> {
				air.set(airWatcher.getValue() >= 300 ? 0 : airWatcher.getValue());
			});
			mapPut.accept(PeMetaBase.AIR, new NetworkEntityMetadataShortLe(air.get()));
			mapPut.accept(PeMetaBase.MAX_AIR, new NetworkEntityMetadataShortLe(300));
			// = PE Bounding Box =
			PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
			if (pocketdata == null || pocketdata.getBoundingBox() == null) {
				ProtocolSupport.logWarning("PE BoundingBox missing for entity: " + entity.getType());
			} else {
				if (pocketdata.getBoundingBox() != null) {
					mapPut.accept(PeMetaBase.BOUNDINGBOX_WIDTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					mapPut.accept(PeMetaBase.BOUNDINGBOX_HEIGTH, new NetworkEntityMetadataObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				}
			}
			// = PE Size =
			mapPut.accept(PeMetaBase.SCALE, new NetworkEntityMetadataObjectFloatLe(entitySize));
			// = PE Interaction =
			String interactText = PEMetaProviderSPI.getProvider().getUseText(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
			if (interactText != null) {
				if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_10)) {
					mapPut.accept(PeMetaBase.BUTTON_TEXT_V1, new NetworkEntityMetadataObjectString(interactText));
				} else {
					mapPut.accept(PeMetaBase.BUTTON_TEXT_V2, new NetworkEntityMetadataObjectString(interactText));
				}
			}
		}
	}

	public BaseEntityMetadataRemapper() {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL_PE) {
			addRemap(new PEDataWatcherRemapper(version), version);
		}
		addRemap(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_GRAVITY}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeFlagRemapper(NetworkEntityMetadataObjectIndex.Entity.FLAGS,
			new int[] {1, 2, 4, 5, 6, 8}, new int[] {
				PeMetaBase.FLAG_ON_FIRE,
				PeMetaBase.FLAG_SNEAKING,
				PeMetaBase.FLAG_SPRINTING,
				PeMetaBase.FLAG_SWIMMING,
				PeMetaBase.FLAG_INVISIBLE,
				PeMetaBase.FLAG_GLIDING}
		), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeSimpleFlagRemapper(NetworkEntityMetadataObjectIndex.Entity.SILENT, PeMetaBase.FLAG_SILENT), ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeSimpleFlagRemapper(NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY, -PeMetaBase.FLAG_GRAVITY), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.UP_1_9);
		addRemap(new IndexValueRemapperNumberToShort(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.UP_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.UP_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.POSE, 6), ProtocolVersionsHelper.UP_1_14);
	}

}
