//TODO Find the stuff in here that needs to be ported.
/*package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperDirectionToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToFloatLe;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToSVarLong;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperStringClamp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeFlagRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeSimpleFlagAdder;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeSimpleFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectVector3fLe;
import protocolsupport.protocol.types.networkentity.metadata.objects.DataWatcherObjectVector3vi;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityLamaDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.Utils;

public enum SpecificRemapper {

	NONE(NetworkEntityType.NONE),
	ENTITY(NetworkEntityType.ENTITY,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				NetworkEntityDataCache data = entity.getDataCache();
				float entitySize = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType()) * data.getSizeModifier();
				// = PE Lead =
				//Leashing is set in Entity Leash.
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_LEASHED, data.getAttachedId() != -1);
				remapped.put(PeMetaBase.LEADHOLDER, new DataWatcherObjectSVarLong(data.getAttachedId()));
				// = PE Nametag =
				Optional<DataWatcherObjectString> nameTagWatcher = DataWatcherObjectIndex.Entity.NAMETAG.getValue(original);
				//Doing this for players makes nametags behave weird or only when close.
				boolean doNametag = ((nameTagWatcher.isPresent()) && (entity.getType() != NetworkEntityType.PLAYER));
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, doNametag);
				if (doNametag) {
					remapped.put(PeMetaBase.NAMETAG, nameTagWatcher.get());
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
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_GRAVITY}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Entity.FLAGS,
			new int[] {1, 2, 4, 6, 8}, new int[] {PeMetaBase.FLAG_ON_FIRE, PeMetaBase.FLAG_SNEAKING, PeMetaBase.FLAG_SPRINTING, PeMetaBase.FLAG_INVISIBLE, PeMetaBase.FLAG_GLIDING}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.SILENT, PeMetaBase.FLAG_SILENT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.NO_GRAVITY, -PeMetaBase.FLAG_GRAVITY), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.FLAGS, 0), ProtocolVersionsHelper.ALL_PC),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNumberToShort(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	LIVING(NetworkEntityType.LIVING, SpecificRemapper.ENTITY,
		new Entry(new PeSimpleFlagAdder(
			new int[] {PeMetaBase.FLAG_CAN_CLIMB}, new boolean[] {true}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.EntityLiving.HAND_USE,
			new int[] {1}, new int[] {PeMetaBase.FLAG_USING_ITEM}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, PeMetaBase.POTION_COLOR), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, PeMetaBase.POTION_AMBIENT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new FirstMetaDataAddRemapper(PeMetaBase.HEALTH, new DataWatcherObjectVarInt(10)), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG, 2), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperStringClamp(DataWatcherObjectIndex.Entity.NAMETAG, 10, 64), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperStringClamp(DataWatcherObjectIndex.Entity.NAMETAG, 5, 64), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 11), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 6), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 6), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(NetworkEntityType.INSENTIENT, SpecificRemapper.LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 10), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 15), ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(NetworkEntityType.PLAYER, SpecificRemapper.LIVING,
		new Entry(new PeSimpleFlagAdder(new int[] {PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG}, new boolean[] {true}), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 12),  ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 11),  ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Player.SCORE, 18),  ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.ALL_1_12),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.ALL_1_12),
		new Entry(new DataWatcherDataRemapper() {
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
		}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	AGEABLE(NetworkEntityType.AGEABLE, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Ageable.IS_BABY.getValue(original).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().setSizeModifier(boolWatcher.getValue() ? 0.5f : 1f);
				});
				//Send scale -> avoid big mobs with floating heads.
				remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entity.getDataCache().getSizeModifier() * PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7)
	),
	TAMEABLE(NetworkEntityType.TAMEABLE, SpecificRemapper.AGEABLE,
		new Entry(new PeFlagRemapper(
			DataWatcherObjectIndex.Tameable.TAME_FLAGS, new int[] {1, 2, 3}, new int[] {PeMetaBase.FLAG_SITTING, PeMetaBase.FLAG_ANGRY, PeMetaBase.FLAG_TAMED}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Tameable.TAME_FLAGS.getValue(original).ifPresent(byteWatcher -> {
					// If the entity is tamed set owner meta to a dummy entity ID
					if ((byteWatcher.getValue() & 0x04) == 0x04) {
						remapped.put(PeMetaBase.OWNER, new DataWatcherObjectSVarLong(0));
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 12),ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(NetworkEntityType.ARMOR_STAND, SpecificRemapper.LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 10), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 11), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 12), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 13), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 15), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 15), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 17), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_8__1_9)
	),
	COW(NetworkEntityType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(NetworkEntityType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(NetworkEntityType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(NetworkEntityType.SQUID, SpecificRemapper.INSENTIENT),
	BASE_HORSE(NetworkEntityType.BASE_HORSE, SpecificRemapper.AGEABLE,
		//TODO: Eating?
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.BaseHorse.FLAGS,
			new int[] {2, 3, 3, 3, 4, 5, 6, 7},
			new int[] {PeMetaBase.FLAG_TAMED, PeMetaBase.FLAG_SADDLED, PeMetaBase.FLAG_WASD_CONTROLLED, PeMetaBase.FLAG_CAN_POWER_JUMP, PeMetaBase.FLAG_IN_LOVE, PeMetaBase.FLAG_USING_ITEM, PeMetaBase.FLAG_REARING, PeMetaBase.FLAG_BREATHING}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new DataWatcherDataRemapper(){
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.BaseHorse.FLAGS.getValue(original).ifPresent(byteWatcher -> {
					remapped.put(PeMetaBase.EATING_HAYSTACK, new DataWatcherObjectVarInt(((byteWatcher.getValue() & (1 << (6-1))) != 0) ? 0b100000 : 0));
					if ((byteWatcher.getValue() & (1 << (2-1))) != 0) {
						//When tamed set these weird properties to make the inventory work. FFS Mojang.
						remapped.put(PeMetaBase.HORSE_CONTAINER_TYPE, new DataWatcherObjectByte((byte) PEDataValues.WINDOWTYPE.getTable(ProtocolVersionsHelper.ALL_PE).getRemap(WindowType.HORSE.toLegacyId()))); //Inventory Type
						remapped.put(PeMetaBase.HORSE_ANIMAL_SLOTS, new DataWatcherObjectSVarInt(2)); //Animal slots (left side of the image)
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BaseHorse.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	BATTLE_HORSE(NetworkEntityType.BATTLE_HORSE, SpecificRemapper.BASE_HORSE,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.BattleHorse.VARIANT.getValue(original)
				.ifPresent(variant -> {
					int variantValue = variant.getValue();
					int baseColor = variantValue & 0x7;
					int markings = (variantValue >> 8) & 0x7;
					remapped.put(PeMetaBase.VARIANT,  new DataWatcherObjectSVarInt(baseColor));
					remapped.put(PeMetaBase.MARK_VARIANT, new DataWatcherObjectSVarInt(markings));
				});
			}
		},  ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.VARIANT, 20), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 17), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.ARMOR, 22), ProtocolVersionsHelper.BEFORE_1_9)
	),
	CARGO_HORSE(NetworkEntityType.CARGO_HORSE, SpecificRemapper.BASE_HORSE,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.CargoHorse.HAS_CHEST.getValue(original)
				.ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_CHESTED, boolWatcher.getValue());
					remapped.put(PeMetaBase.HORSE_CONTAINER_MULTIPLIER, new DataWatcherObjectSVarInt(boolWatcher.getValue() ? 3 : 0)); //Strength multiplier for chest size.
				});
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.CargoHorse.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	COMMON_HORSE(NetworkEntityType.COMMON_HORSE, SpecificRemapper.BATTLE_HORSE),
	ZOMBIE_HORSE(NetworkEntityType.ZOMBIE_HORSE, SpecificRemapper.BATTLE_HORSE,
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(3)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(3)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 3)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	SKELETON_HORSE(NetworkEntityType.SKELETON_HORSE, SpecificRemapper.BATTLE_HORSE,
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(4)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(4)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 4)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	DONKEY(NetworkEntityType.DONKEY, SpecificRemapper.CARGO_HORSE,
		new Entry(new FirstMetaDataAddRemapper(PeMetaBase.STRENGTH, new DataWatcherObjectSVarInt(5)), ProtocolVersionsHelper.ALL_PE), //Fake strength for when chested.
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 1)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	MULE(NetworkEntityType.MULE, SpecificRemapper.CARGO_HORSE,
		new Entry(new FirstMetaDataAddRemapper(PeMetaBase.STRENGTH, new DataWatcherObjectSVarInt(5)), ProtocolVersionsHelper.ALL_PE), //Fake strength for when chested.
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(2)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 2)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	LAMA(NetworkEntityType.LAMA, SpecificRemapper.CARGO_HORSE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE),
		//new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Lama.CARPET_COLOR, 3), ProtocolVersionsHelper.ALL_PE), TODO: Carpet Color. Done via slots instead?
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.STRENGTH, PeMetaBase.STRENGTH), ProtocolVersionsHelper.ALL_PE), //TODO: Should max strength also be added?
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Lama.STRENGTH.getValue(original).ifPresent(intWatcher -> {
					((NetworkEntityLamaDataCache) entity.getDataCache()).setStrength(intWatcher.getValue());
				});
			}}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	BAT(NetworkEntityType.BAT, SpecificRemapper.INSENTIENT,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Bat.HANGING,
			new int[] {1, 1}, new int[] {PeMetaBase.FLAG_RESTING, -PeMetaBase.FLAG_GRAVITY}), //If the bat is hanging, remove it's gravity to prevent it from falling.
		ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(NetworkEntityType.OCELOT, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ocelot.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ocelot.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Ocelot.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(NetworkEntityType.WOLF, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, PeMetaBase.COLOR) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 15), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 18), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wolf.HEALTH, 18), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 17), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 16), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(NetworkEntityType.PIG, SpecificRemapper.AGEABLE,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Pig.HAS_SADLLE, PeMetaBase.FLAG_SADDLED), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.BOOST_TIME, 14), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2)
	),
	RABBIT(NetworkEntityType.RABBIT, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Rabbit.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(NetworkEntityType.SHEEP, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapper<Byte, DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, PeMetaBase.COLOR) {
			@Override
			public DataWatcherObjectByte remapValue(DataWatcherObjectByte object) {
				return new DataWatcherObjectByte((byte) (object.getValue() & 0x0F));
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Sheep.FLAGS,
			new int[] {5}, new int[] {PeMetaBase.FLAG_SHEARED}),
		ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	POLAR_BEAR(NetworkEntityType.POLAR_BEAR, SpecificRemapper.AGEABLE,
		//TODO: Just like horses, disappears. Perhaps send a unknown entitystatus aswell? Meh.
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.PolarBear.STANDING_UP, PeMetaBase.FLAG_REARING), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PolarBear.STANDING_UP, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	VILLAGER(NetworkEntityType.VILLAGER, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, PeMetaBase.VARIANT) {
			@Override
			public DataWatcherObjectSVarInt remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectSVarInt(object.getValue() == 5 ? 0 : object.getValue()); //TODO: use regular remapper when nitwit is implemented.
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Villager.PROFESSION, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(NetworkEntityType.ENDERMAN, SpecificRemapper.INSENTIENT,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Enderman.SCREAMING, PeMetaBase.FLAG_VIBRATING), ProtocolVersionsHelper.ALL_PE),
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Enderman.CARRIED_BLOCK.getValue(original).ifPresent(stateWatcher -> {
					remapped.put(PeMetaBase.ENDERMAN_BLOCK, new DataWatcherObjectShortLe((short) MinecraftData.getBlockIdFromState(stateWatcher.getValue())));
				});
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectShort((short) MinecraftData.getBlockIdFromState(object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) MinecraftData.getBlockIdFromState(object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 17) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) MinecraftData.getBlockDataFromState(object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(NetworkEntityType.GIANT, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				entity.getDataCache().setSizeModifier(6f);
				float entitySize = 6f * PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
				remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize)); //Send scale -> Giants are Giant Zombies in PE.
				PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
				if (pocketdata.getBoundingBox() != null) {
					remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				}
			}
		}, ProtocolVersionsHelper.ALL_PE)),
	SILVERFISH(NetworkEntityType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(NetworkEntityType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(NetworkEntityType.ENDER_DRAGON, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderDragon.PHASE, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderDragon.PHASE, 11), ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWMAN(NetworkEntityType.SNOWMAN, SpecificRemapper.INSENTIENT,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Snowman.NO_HAT,
				new int[] {5}, new int[] {-PeMetaBase.FLAG_SHEARED}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Snowman.NO_HAT, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Snowman.NO_HAT, 11), ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE(NetworkEntityType.ZOMBIE, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Zombie.BABY.getValue(original).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					float sizescale = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
					float entitySize = boolWatcher.getValue() ? 0.5f * sizescale : sizescale;
					remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize)); //Send scale -> avoid big mobs with floating heads.
					PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
					if (pocketdata.getBoundingBox() != null) {
						remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
						remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
					}
				});
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 15), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE_VILLAGER(NetworkEntityType.ZOMBIE_VILLAGER, SpecificRemapper.ZOMBIE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 15), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersionsHelper.BEFORE_1_9)
	),
	HUSK(NetworkEntityType.HUSK, SpecificRemapper.ZOMBIE),
	ZOMBIE_PIGMAN(NetworkEntityType.ZOMBIE_PIGMAN, SpecificRemapper.ZOMBIE),
	BLAZE(NetworkEntityType.BLAZE, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(NetworkEntityType.SPIDER, SpecificRemapper.LIVING,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Spider.CLIMBING,
			new int[] {1}, new int[] {PeMetaBase.FLAG_CLIMBING}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(NetworkEntityType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(NetworkEntityType.CREEPER, SpecificRemapper.INSENTIENT,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Creeper.IGNITED, PeMetaBase.FLAG_IGNITED), ProtocolVersionsHelper.ALL_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Creeper.POWERED, PeMetaBase.FLAG_CHARGED), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.STATE, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.STATE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Creeper.STATE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.POWERED, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.POWERED, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.POWERED, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.IGNITED, 14), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.IGNITED, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.IGNITED, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(NetworkEntityType.GHAST, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ghast.ATTACKING, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ghast.ATTACKING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Ghast.ATTACKING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(NetworkEntityType.SLIME, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Slime.SIZE.getValue(original).ifPresent(intWatcher -> {
					entity.getDataCache().setSizeModifier(intWatcher.getValue());
				});
				float entitySize = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType()) * entity.getDataCache().getSizeModifier();
				remapped.put(PeMetaBase.SCALE, new DataWatcherObjectFloatLe(entitySize)); //Send slime scale.
				PEEntityData pocketdata = PEDataValues.getEntityData(entity.getType());
				if (pocketdata.getBoundingBox() != null) {
					remapped.put(PeMetaBase.BOUNDINGBOX_WIDTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getWidth() * entitySize));
					remapped.put(PeMetaBase.BOUNDINGBOX_HEIGTH, new DataWatcherObjectFloatLe(pocketdata.getBoundingBox().getHeight() * entitySize));
				}
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Slime.SIZE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(NetworkEntityType.MAGMA_CUBE, SpecificRemapper.SLIME),
	BASE_SKELETON(NetworkEntityType.BASE_SKELETON, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 13), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.ALL_1_9)
	),
	SKELETON(NetworkEntityType.SKELETON, SpecificRemapper.BASE_SKELETON),
	WITHER_SKELETON(NetworkEntityType.WITHER_SKELETON, SpecificRemapper.BASE_SKELETON,
		new Entry(new FirstMetaDataAddRemapper(12, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(11, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectByte((byte) 1)), ProtocolVersionsHelper.BEFORE_1_9)
	),
	STRAY(NetworkEntityType.STRAY, SpecificRemapper.BASE_SKELETON,
		new Entry(new FirstMetaDataAddRemapper(12, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10)
	),
	WITCH(NetworkEntityType.WITCH, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Witch.DRINKING_POTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Witch.DRINKING_POTION, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Witch.DRINKING_POTION, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(NetworkEntityType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(NetworkEntityType.SHULKER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapper<Byte, DataWatcherObjectByte>(DataWatcherObjectIndex.Shulker.COLOR, PeMetaBase.COLOR) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectByte object) {
				return new DataWatcherObjectSVarInt(15 - object.getValue());
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, PeMetaBase.SHULKER_HEIGHT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperDirectionToByte(DataWatcherObjectIndex.Shulker.DIRECTION, PeMetaBase.SHULKER_DIRECTION), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<Position, DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, PeMetaBase.SHULKER_ATTACH_POS) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectOptionalPosition object) {
				return new DataWatcherObjectVector3vi(object.getValue());
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 14), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.COLOR, 15), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	WITHER(NetworkEntityType.WITHER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET1, PeMetaBase.WITHER_TARGET1), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET2, PeMetaBase.WITHER_TARGET2), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET3, PeMetaBase.WITHER_TARGET3), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, PeMetaBase.INVULNERABLE_TIME), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET1, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET2, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 14), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET3, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 15), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(NetworkEntityType.GUARDIAN, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.SPIKES, 12), ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 11) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.TARGET_ID, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.TARGET_ID, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Guardian.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8)
	),
	ELDER_GUARDIAN(NetworkEntityType.ELDER_GUARDIAN, SpecificRemapper.GUARDIAN),
	VINDICATOR(NetworkEntityType.VINDICATOR, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Vindicator.HAS_TARGET, 12), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	EVOKER(NetworkEntityType.EVOKER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Evoker.SPELL, 12), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	ILLUSIONER(NetworkEntityType.ILLUSIONER, SpecificRemapper.EVOKER),
	VEX(NetworkEntityType.VEX, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Vex.FLAGS, 12), ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	PARROT(NetworkEntityType.PARROT, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Parrot.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Parrot.VARIANT, 15), ProtocolVersionsHelper.ALL_1_12)
	),
	ARMOR_STAND_MOB(NetworkEntityType.ARMOR_STAND_MOB, SpecificRemapper.ARMOR_STAND),
	BOAT(NetworkEntityType.BOAT, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Boat.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.LEFT_PADDLE, PeMetaBase.PADDLE_TIME_LEFT) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				//TODO: Actually increment and send correct 'paddletime'.
				return new DataWatcherObjectFloatLe(object.getValue() ? 0.05f: 0f);
			}},ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapper<Boolean, DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, PeMetaBase.PADDLE_TIME_RIGHT) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				//TODO: Actually increment and send correct 'paddletime'.
				return new DataWatcherObjectFloatLe(object.getValue() ? 0.05f: 0f);
			}},ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.VARIANT, 9), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.LEFT_PADDLE, 10), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	TNT(NetworkEntityType.TNT, SpecificRemapper.ENTITY,
		new Entry(new PeSimpleFlagAdder(
			new int[] {PeMetaBase.FLAG_IGNITED}, new boolean[] {true}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Tnt.FUSE, PeMetaBase.FUSE_LENGTH), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWBALL(NetworkEntityType.SNOWBALL, SpecificRemapper.ENTITY),
	EGG(NetworkEntityType.EGG, SpecificRemapper.ENTITY),
	FIREBALL(NetworkEntityType.FIREBALL, SpecificRemapper.ENTITY),
	FIRECHARGE(NetworkEntityType.FIRECHARGE, SpecificRemapper.ENTITY),
	ENDERPEARL(NetworkEntityType.ENDERPEARL, SpecificRemapper.ENTITY),
	WITHER_SKULL(NetworkEntityType.WITHER_SKULL, SpecificRemapper.FIREBALL,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.WitherSkull.CHARGED, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.WitherSkull.CHARGED, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.WitherSkull.CHARGED, 10), ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(NetworkEntityType.FALLING_OBJECT, SpecificRemapper.ENTITY),
	ENDEREYE(NetworkEntityType.ENDEREYE, SpecificRemapper.ENTITY),
	POTION(NetworkEntityType.POTION, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 7), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	EXP_BOTTLE(NetworkEntityType.EXP_BOTTLE, SpecificRemapper.ENTITY),
	LEASH_KNOT(NetworkEntityType.LEASH_KNOT, SpecificRemapper.ENTITY),
	FISHING_FLOAT(NetworkEntityType.FISHING_FLOAT, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, PeMetaBase.OWNER), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	ITEM(NetworkEntityType.ITEM, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 10), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(NetworkEntityType.MINECART, SpecificRemapper.ENTITY,
		//PE TODO: Damagetime and shake direction & block remapping :F
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK, PeMetaBase.MINECART_BLOCK), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, PeMetaBase.MINECART_OFFSET), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, PeMetaBase.MINECART_DISPLAY), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK, 9), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK, 8), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK, 20), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				int value = object.getValue();
				int id = value & 0xFFFF;
				int data = value >> 12;
				return new DataWatcherObjectInt((data << 16) | id);
			}
		}, ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART_CHEST(NetworkEntityType.MINECART_CHEST, SpecificRemapper.MINECART),
	MINECART_FURNACE(NetworkEntityType.MINECART_FURNACE, SpecificRemapper.MINECART,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				//Simulate furnaceMinecart in Pocket.
				remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(61));
				DataWatcherObjectIndex.MinecartFurnace.POWERED.getValue(original).ifPresent(boolWatcher -> {if(boolWatcher.getValue()) {
					remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(62));
				}});
				remapped.put(PeMetaBase.MINECART_OFFSET, new DataWatcherObjectSVarInt(6));
				remapped.put(PeMetaBase.MINECART_DISPLAY, new DataWatcherObjectByte((byte) 1));
			}
		}, ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART_TNT(NetworkEntityType.MINECART_TNT, SpecificRemapper.MINECART),
	MINECART_SPAWNER(NetworkEntityType.MINECART_MOB_SPAWNER, SpecificRemapper.MINECART,
			new Entry(new DataWatcherDataRemapper() {
				@Override
				public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
					//Simulate spawnerMinecart in Pocket.
					remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(52));
					remapped.put(PeMetaBase.MINECART_OFFSET, new DataWatcherObjectSVarInt(6));
					remapped.put(PeMetaBase.MINECART_DISPLAY, new DataWatcherObjectByte((byte) 1));
				}
			}, ProtocolVersionsHelper.ALL_PE)),
	MINECART_HOPPER(NetworkEntityType.MINECART_HOPPER, SpecificRemapper.MINECART),
	MINECART_COMMAND(NetworkEntityType.MINECART_COMMAND, SpecificRemapper.MINECART,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, PeMetaBase.COMMAND_COMMAND), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 12), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 23), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, PeMetaBase.COMMAND_LAST_OUTPUT), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 13), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 24), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8))
	),
	ARROW(NetworkEntityType.ARROW, SpecificRemapper.ENTITY,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Arrow.CIRTICAL,
			new int[] {1}, new int[] {PeMetaBase.FLAG_CRITICAL}
		), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 15), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(NetworkEntityType.SPECTRAL_ARROW, SpecificRemapper.ARROW),
	TIPPED_ARROW(NetworkEntityType.TIPPED_ARROW, SpecificRemapper.ARROW,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 6), ProtocolVersionsHelper.ALL_1_9)
	),
	FIREWORK(NetworkEntityType.FIREWORK, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, PeMetaBase.FIREWORK_TYPE), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 8), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.USER, 7), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2)
	),
	ITEM_FRAME(NetworkEntityType.ITEM_FRAME, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 8), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 2), ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ROTATION, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ROTATION, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.ItemFrame.ROTATION, 9), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectVarInt>(DataWatcherObjectIndex.ItemFrame.ROTATION, 3) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(NetworkEntityType.ENDER_CRYSTAL, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.TARGET, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.TARGET, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 6), ProtocolVersionsHelper.ALL_1_9)
	),
	ARMOR_STAND_OBJECT(NetworkEntityType.ARMOR_STAND_OBJECT, SpecificRemapper.ARMOR_STAND),
	AREA_EFFECT_CLOUD(NetworkEntityType.AREA_EFFECT_CLOUD, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNumberToFloatLe(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, PeMetaBase.AREA_EFFECT_RADIUS), ProtocolVersionsHelper.ALL_PE),
		//TODO: area effectcloud waiting?
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, PeMetaBase.AREA_EFFECT_PARTICLE), ProtocolVersionsHelper.ALL_PE),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 8), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA1, 10), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA1, 9), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA2, 11), ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA2, 10), ProtocolVersionsHelper.ALL_1_9)
	),
	SHULKER_BULLET(NetworkEntityType.SHULKER_BULLET, SpecificRemapper.ENTITY),
	LAMA_SPIT(NetworkEntityType.LAMA_SPIT, SpecificRemapper.ENTITY),
	DRAGON_FIREBALL(NetworkEntityType.DRAGON_FIREBALL, SpecificRemapper.ENTITY),
	EVOCATOR_FANGS(NetworkEntityType.EVOCATOR_FANGS, SpecificRemapper.ENTITY);

	private static final Map<NetworkEntityType, SpecificRemapper> wtype = CollectionsUtils.makeEnumMappingEnumMap(SpecificRemapper.class, NetworkEntityType.class, (e -> e.type));

	public static SpecificRemapper fromWatchedType(NetworkEntityType type) {
		return wtype.getOrDefault(type, SpecificRemapper.NONE);
	}

	private final NetworkEntityType type;
	private final EnumMap<ProtocolVersion, List<DataWatcherDataRemapper>> entries = new EnumMap<>(ProtocolVersion.class);

	SpecificRemapper(NetworkEntityType type, Entry... entries) {
		this.type = type;
		for (Entry entry : entries) {
			for (ProtocolVersion version : entry.versions) {
				Utils.getFromMapOrCreateDefault(this.entries, version, new ArrayList<DataWatcherDataRemapper>()).add(entry.remapper);
			}
		}
	}

	SpecificRemapper(NetworkEntityType type, SpecificRemapper superType, Entry... entries) {
		this.type = type;
		for (Map.Entry<ProtocolVersion, List<DataWatcherDataRemapper>> entry : superType.entries.entrySet()) {
			Utils.getFromMapOrCreateDefault(this.entries, entry.getKey(), new ArrayList<DataWatcherDataRemapper>()).addAll(entry.getValue());
		}
		for (Entry entry : entries) {
			for (ProtocolVersion version : entry.versions) {
				Utils.getFromMapOrCreateDefault(this.entries, version, new ArrayList<DataWatcherDataRemapper>()).add(entry.remapper);
			}
		}
	}

	public List<DataWatcherDataRemapper> getRemaps(ProtocolVersion version) {
		return entries.getOrDefault(version, Collections.emptyList());
	}

	private static class Entry {
		private final DataWatcherDataRemapper remapper;
		private final List<ProtocolVersion> versions;
		public Entry(DataWatcherDataRemapper remapper, ProtocolVersion... versions) {
			this.remapper = remapper;
			this.versions = Arrays.asList(versions);
		}
	}

}
*/