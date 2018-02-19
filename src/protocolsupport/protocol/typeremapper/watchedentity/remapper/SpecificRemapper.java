package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperDirectionToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToFloatLe;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.IndexValueRemapperStringClamp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeFlagRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeSimpleFlagAdder;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.PeSimpleFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNBTTagCompound;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3fLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3vi;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache.Rider;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.Utils;

public enum SpecificRemapper {

	NONE(NetworkEntityType.NONE),
	ENTITY(NetworkEntityType.ENTITY,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				// = PE Lead =
				//Leashing is set in Entity Leash.
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_LEASHED, entity.getDataCache().attachedId != -1);
				remapped.put(38, new DataWatcherObjectSVarLong(entity.getDataCache().attachedId));

				// = PE Nametag =
				//Doing this for players makes nametags behave weird or only when close.
				DataWatcherObject<?> nameTagWatcher = original.get(DataWatcherObjectIndex.Entity.NAMETAG);
				boolean doNametag = ((nameTagWatcher != null) && (entity.getType() != NetworkEntityType.PLAYER));
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_SHOW_NAMETAG, doNametag);
				if (doNametag) { remapped.put(4, nameTagWatcher); }

				// = PE Riding =
				Rider rider = entity.getDataCache().rider;
				entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_RIDING, rider.riding);
				if (rider.riding) {
					//Extra data PE needs for vehicles. Set in setPassenger.
					remapped.put(57, new DataWatcherObjectVector3fLe(rider.position));
					remapped.put(58, new DataWatcherObjectByte((byte) ((rider.rotationLocked) ? 1 : 0)));
					if(rider.rotationMax != null) {
						remapped.put(59, new DataWatcherObjectFloatLe(rider.rotationMax));
					}
					if(rider.rotationMin != null) {
						remapped.put(60, new DataWatcherObjectFloatLe(rider.rotationMin));
					}
				}

				// = PE Air =
				AtomicInteger air = new AtomicInteger(0);
				getObject(original, DataWatcherObjectIndex.Entity.AIR, DataWatcherObjectVarInt.class).ifPresent(airWatcher -> {
					air.set(airWatcher.getValue() >= 300 ? 0 : airWatcher.getValue());
				});
				remapped.put(7, new DataWatcherObjectShortLe(air.get()));

				// = PE Bounding Box =
				//TODO: Put real numbers in? This will allow mobs to push PE player properly and such xD Perhaps necessary for PE Lighting?
				remapped.put(54, new DataWatcherObjectFloatLe(0.6f)); //Width
				remapped.put(55, new DataWatcherObjectFloatLe(1.8f)); //Height

				// = PE Interaction =
				remapped.put(40, new DataWatcherObjectString(PEMetaProviderSPI.getProvider().getUseText(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));

				// = PE Size =
				remapped.put(39, new DataWatcherObjectFloatLe(PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));

			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeSimpleFlagAdder(
				new int[] {PeMetaBase.FLAG_GRAVITY}, new boolean[] {true}
			), ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Entity.FLAGS,
				new int[] {1, 2, 4, 6, 8}, new int[] {PeMetaBase.FLAG_ON_FIRE, PeMetaBase.FLAG_SNEAKING, PeMetaBase.FLAG_SPRINTING, PeMetaBase.FLAG_INVISIBLE, PeMetaBase.FLAG_GLIDING}
		), ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.SILENT, PeMetaBase.FLAG_SILENT), ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Entity.NO_GRAVITY, -PeMetaBase.FLAG_GRAVITY), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Entity.FLAGS, 0) {}, ProtocolVersionsHelper.ALL_PC),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Entity.AIR, 1) {}, ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNumberToShort(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.Entity.NAMETAG, 2) {}, ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3) {}, ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Entity.SILENT, 4) {}, ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Entity.NO_GRAVITY, 5) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	LIVING(NetworkEntityType.LIVING, SpecificRemapper.ENTITY,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.EntityLiving.HAND_USE,
				new int[] {1}, new int[] {PeMetaBase.FLAG_USING_ITEM}
		), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8) {}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersion.MINECRAFT_PE),
		new Entry(new FirstMetaDataAddRemapper(1, new DataWatcherObjectVarInt(10)), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.Entity.NAMETAG, 2) {}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperStringClamp(DataWatcherObjectIndex.Entity.NAMETAG, 10, 64), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperStringClamp(DataWatcherObjectIndex.Entity.NAMETAG, 5, 64), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 11), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 6), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.EntityLiving.HAND_USE, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.EntityLiving.HAND_USE, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.EntityLiving.HEALTH, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.EntityLiving.HEALTH, 6) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(NetworkEntityType.INSENTIENT, SpecificRemapper.LIVING,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Insentient.NO_AI, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Insentient.NO_AI, 10) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Insentient.NO_AI, 15) {}, ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(NetworkEntityType.PLAYER, SpecificRemapper.LIVING,
		new Entry(new PeSimpleFlagAdder(
				new int[] {PeMetaBase.FLAG_ALWAYS_SHOW_NAMETAG, PeMetaBase.FLAG_COLLIDE}, new boolean[] {true, true}
			), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 10) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 17) {}, ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Player.SCORE, 12) {},  ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Player.SCORE, 11) {},  ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Player.SCORE, 18),  ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Player.SKIN_FLAGS, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Player.SKIN_FLAGS, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Player.SKIN_FLAGS, 10) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Player.MAIN_HAND, 14) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Player.MAIN_HAND, 13) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectNBTTagCompound>(DataWatcherObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15) {}, ProtocolVersionsHelper.ALL_1_12),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectNBTTagCompound>(DataWatcherObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16) {}, ProtocolVersionsHelper.ALL_1_12),
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Entity.FLAGS, DataWatcherObjectByte.class)
				.ifPresent(baseflags -> entity.getDataCache().setPcBaseFlags(baseflags.getValue()));
				getObject(original, DataWatcherObjectIndex.EntityLiving.HAND_USE, DataWatcherObjectByte.class)
				.ifPresent(activehandflags -> {
					byte activehandvalue = activehandflags.getValue();
					byte basevalue = entity.getDataCache().getPcBaseFlags();
					switch (activehandvalue) {
						case 1: {
							basevalue |= (1 << 4);
							break;
						}
						case 0: {
							basevalue &= ~(1 << 4);
							break;
						}
						default: {
							break;
						}
					}
					remapped.put(0, new DataWatcherObjectByte(basevalue));
				});
			}
		}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	AGEABLE(NetworkEntityType.AGEABLE, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Ageable.AGE, DataWatcherObjectBoolean.class).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					entity.getDataCache().sizeModifier = (byte) (boolWatcher.getValue() ? 0.5 : 1);
				});
				//Send scale -> avoid big mobs with floating heads.
				remapped.put(39, new DataWatcherObjectFloatLe(entity.getDataCache().sizeModifier * PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				)));
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.AGE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.AGE, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.AGE, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.AGE, 12) {
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
			), ProtocolVersion.MINECRAFT_PE),
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Tameable.TAME_FLAGS, DataWatcherObjectByte.class).ifPresent(byteWatcher -> {
					if ((byteWatcher.getValue() & 0x04) == 0x04) { // If the entity is tamed
						remapped.put(5, new DataWatcherObjectSVarLong(0)); // set owner meta to a dummy entity ID
					}
				});
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 12) {},ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(NetworkEntityType.ARMOR_STAND, SpecificRemapper.LIVING,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.ArmorStand.FLAGS, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.ArmorStand.FLAGS, 10) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 11) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 12) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 14) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 13) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 15) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 14) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 16) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 15) {}, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 17) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVector3f>(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 16) {}, ProtocolVersionsHelper.RANGE__1_8__1_9)
	),
	COW(NetworkEntityType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(NetworkEntityType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(NetworkEntityType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(NetworkEntityType.SQUID, SpecificRemapper.INSENTIENT),
	BASE_HORSE(NetworkEntityType.BASE_HORSE, SpecificRemapper.AGEABLE,
		//TODO: Not thse flags? These just make the horse disappear :/ PeMetaBase.FLAG_REARING PeMetaBase.FLAG_BREATHING
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.BaseHorse.FLAGS,
				new int[] {2, 3, 4}, new int[] {PeMetaBase.FLAG_TAMED, PeMetaBase.FLAG_SADDLED, PeMetaBase.FLAG_CHESTED}),
			ProtocolVersion.MINECRAFT_PE),
		new Entry(new DataWatcherDataRemapper(){
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.BaseHorse.FLAGS, DataWatcherObjectByte.class).ifPresent(byteWatcher -> {
					if((byteWatcher.getValue() & (1 << (6-1))) != 0) {
						//TODO: Store PE horseflags when neeeded.
						remapped.put(16, new DataWatcherObjectVarInt(0b100000));
					}
				});
			}
		}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.BaseHorse.FLAGS, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.BaseHorse.FLAGS, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BaseHorse.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	BATTLE_HORSE(NetworkEntityType.BATTLE_HORSE, SpecificRemapper.BASE_HORSE,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.BattleHorse.VARIANT, DataWatcherObjectVarInt.class)
				.ifPresent(variant -> {
					int variantValue = variant.getValue();
					int baseColor = variantValue & 0x7;
					int markings = (variantValue >> 8) & 0x7;
					remapped.put(2,  new DataWatcherObjectSVarInt(baseColor));
					remapped.put(44, new DataWatcherObjectSVarInt(markings));
				});
			}
		},  ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.BattleHorse.VARIANT, 15) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.BattleHorse.VARIANT, 14) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.VARIANT, 20), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.BattleHorse.ARMOR, 16) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.BattleHorse.ARMOR, 17) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.BattleHorse.ARMOR, 16) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.ARMOR, 22), ProtocolVersionsHelper.BEFORE_1_9)
	),
	CARGO_HORSE(NetworkEntityType.CARGO_HORSE, SpecificRemapper.BASE_HORSE,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.CargoHorse.HAS_CHEST, 15) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
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
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 1)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	MULE(NetworkEntityType.MULE, SpecificRemapper.CARGO_HORSE,
		new Entry(new FirstMetaDataAddRemapper(14, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstMetaDataAddRemapper(13, new DataWatcherObjectVarInt(2)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstMetaDataAddRemapper(19, new DataWatcherObjectByte((byte) 2)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	LAMA(NetworkEntityType.LAMA, SpecificRemapper.CARGO_HORSE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.VARIANT, 2), ProtocolVersion.MINECRAFT_PE),
		//new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Lama.CARPET_COLOR, 3), ProtocolVersion.MINECRAFT_PE), TODO: Carpet Color. Done via slots instead?
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Lama.STRENGTH, 75), ProtocolVersion.MINECRAFT_PE), //TODO: Should max strength also be added?
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Lama.STRENGTH, 16) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Lama.CARPET_COLOR, 17) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Lama.VARIANT, 18) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	BAT(NetworkEntityType.BAT, SpecificRemapper.INSENTIENT,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Bat.HANGING,
				new int[] {1, 1}, new int[] {PeMetaBase.FLAG_RESTING, -PeMetaBase.FLAG_GRAVITY}), //If the bat is hanging, remove it's gravity to prevent it from falling.
			ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Bat.HANGING, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Bat.HANGING, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Bat.HANGING, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(NetworkEntityType.OCELOT, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Ocelot.VARIANT, 2), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Ocelot.VARIANT, 15) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Ocelot.VARIANT, 14) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Ocelot.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(NetworkEntityType.WOLF, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 3) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Wolf.BEGGING, PeMetaBase.FLAG_INTERESTED), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Wolf.HEALTH, 15) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Wolf.HEALTH, 14) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Wolf.HEALTH, 18) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wolf.HEALTH, 18), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Wolf.BEGGING, 16) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Wolf.BEGGING, 15) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 17) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 16) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(NetworkEntityType.PIG, SpecificRemapper.AGEABLE,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Pig.HAS_SADLLE, PeMetaBase.FLAG_SADDLED), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Pig.HAS_SADLLE, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Pig.HAS_SADLLE, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Pig.BOOST_TIME, 14) {}, ProtocolVersionsHelper.RANGE__1_11_1__1_12_2)
	),
	RABBIT(NetworkEntityType.RABBIT, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Rabbit.VARIANT, 2), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Rabbit.VARIANT, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Rabbit.VARIANT, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(NetworkEntityType.SHEEP, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapper<DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, 3) {
			@Override
			public DataWatcherObjectByte remapValue(DataWatcherObjectByte object) {
				return new DataWatcherObjectByte((byte) (object.getValue() & 0x0F));
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Sheep.FLAGS,
				new int[] {5}, new int[] {PeMetaBase.FLAG_SHEARED}),
			ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Sheep.FLAGS, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	POLAR_BEAR(NetworkEntityType.POLAR_BEAR, SpecificRemapper.AGEABLE,
		//TODO: Just like horses, disappears. Perhaps send a unknown entitystatus aswell? Meh.
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.PolarBear.STANDING_UP, PeMetaBase.FLAG_REARING), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.PolarBear.STANDING_UP, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	VILLAGER(NetworkEntityType.VILLAGER, SpecificRemapper.AGEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Villager.PROFESSION, 2), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Villager.PROFESSION, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Villager.PROFESSION, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(NetworkEntityType.ENDERMAN, SpecificRemapper.INSENTIENT,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Enderman.SCREAMING, PeMetaBase.FLAG_VIBRATING), ProtocolVersion.MINECRAFT_PE),
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, DataWatcherObjectBlockState.class).ifPresent(stateWatcher -> {
					remapped.put(23, new DataWatcherObjectShortLe((short) MinecraftData.getBlockIdFromState(stateWatcher.getValue())));
					remapped.put(24, new DataWatcherObjectShortLe((short) MinecraftData.getBlockDataFromState(stateWatcher.getValue())));
				});
			}
		}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectShort((short) MinecraftData.getBlockIdFromState(object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) MinecraftData.getBlockIdFromState(object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 17) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) MinecraftData.getBlockDataFromState(object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Enderman.SCREAMING, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Enderman.SCREAMING, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(NetworkEntityType.GIANT, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				remapped.put(39, new DataWatcherObjectFloatLe(6f * PEMetaProviderSPI.getProvider().getSizeScale(
						entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				))); //Send scale -> Giants are Giant Zombies in PE.
			}
		}, ProtocolVersion.MINECRAFT_PE)),
	SILVERFISH(NetworkEntityType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(NetworkEntityType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(NetworkEntityType.ENDER_DRAGON, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EnderDragon.PHASE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.EnderDragon.PHASE, 11) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWMAN(NetworkEntityType.SNOWMAN, SpecificRemapper.INSENTIENT,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Snowman.NO_HAT,
				new int[] {5}, new int[] {-PeMetaBase.FLAG_SHEARED}
		), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Snowman.NO_HAT, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Snowman.NO_HAT, 11) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE(NetworkEntityType.ZOMBIE, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Zombie.BABY, DataWatcherObjectBoolean.class).ifPresent(boolWatcher -> {
					entity.getDataCache().setPeBaseFlag(PeMetaBase.FLAG_BABY, boolWatcher.getValue());
					float sizescale = PEMetaProviderSPI.getProvider().getSizeScale(entity.getUUID(), entity.getId(), entity.getType().getBukkitType());
					remapped.put(39, new DataWatcherObjectFloatLe(boolWatcher.getValue() ? 0.5f * sizescale : sizescale)); //Send scale -> avoid big mobs with floating heads.
				});
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Zombie.BABY, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Zombie.BABY, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Zombie.PROFESSION, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Zombie.PROFESSION, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Zombie.PROFESSION, 13), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Zombie.HANDS_UP, 14) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Zombie.HANDS_UP, 15) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Zombie.HANDS_UP, 14) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE_VILLAGER(NetworkEntityType.ZOMBIE_VILLAGER, SpecificRemapper.ZOMBIE,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 15) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 13) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersionsHelper.BEFORE_1_9)
	),
	HUSK(NetworkEntityType.HUSK, SpecificRemapper.ZOMBIE),
	ZOMBIE_PIGMAN(NetworkEntityType.ZOMBIE_PIGMAN, SpecificRemapper.ZOMBIE),
	BLAZE(NetworkEntityType.BLAZE, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Blaze.ON_FIRE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Blaze.ON_FIRE, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Blaze.ON_FIRE, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(NetworkEntityType.SPIDER, SpecificRemapper.LIVING,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Spider.CLIMBING,
				new int[] {1}, new int[] {PeMetaBase.FLAG_CLIMBING}
		), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Spider.CLIMBING, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Spider.CLIMBING, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Spider.CLIMBING, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(NetworkEntityType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(NetworkEntityType.CREEPER, SpecificRemapper.INSENTIENT,
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Creeper.IGNITED, PeMetaBase.FLAG_IGNITED), ProtocolVersion.MINECRAFT_PE),
		new Entry(new PeSimpleFlagRemapper(DataWatcherObjectIndex.Creeper.POWERED, PeMetaBase.FLAG_CHARGED), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Creeper.STATE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Creeper.STATE, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Creeper.STATE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Creeper.POWERED, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Creeper.POWERED, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.POWERED, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Creeper.IGNITED, 14) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Creeper.IGNITED, 13) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.IGNITED, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(NetworkEntityType.GHAST, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ghast.ATTACKING, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ghast.ATTACKING, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Ghast.ATTACKING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(NetworkEntityType.SLIME, SpecificRemapper.INSENTIENT,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				getObject(original, DataWatcherObjectIndex.Slime.SIZE, DataWatcherObjectVarInt.class).ifPresent(intWatcher -> {
					entity.getDataCache().sizeModifier = (byte) ((int) intWatcher.getValue());
				});
				remapped.put(39, new DataWatcherObjectFloatLe(PEMetaProviderSPI.getProvider().getSizeScale(
					entity.getUUID(), entity.getId(), entity.getType().getBukkitType()
				) * entity.getDataCache().sizeModifier)); //Send slime scale.
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Slime.SIZE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Slime.SIZE, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Slime.SIZE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(NetworkEntityType.MAGMA_CUBE, SpecificRemapper.SLIME),
	BASE_SKELETON(NetworkEntityType.BASE_SKELETON, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Skeleton.ATTACKING, 12) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Skeleton.ATTACKING, 13) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Skeleton.ATTACKING, 12) {}, ProtocolVersionsHelper.ALL_1_9)
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
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Witch.AGGRESSIVE, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Witch.AGGRESSIVE, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Witch.AGGRESSIVE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(NetworkEntityType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 16) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(NetworkEntityType.SHULKER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapper<DataWatcherObjectByte>(DataWatcherObjectIndex.Shulker.COLOR, 2) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectByte object) {
				return new DataWatcherObjectSVarInt(15 - object.getValue());
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 64), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperDirectionToByte(DataWatcherObjectIndex.Shulker.DIRECTION, 65), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapper<DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 67) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectOptionalPosition object) {
				return new DataWatcherObjectVector3vi(object.getValue());
			}}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectDirection>(DataWatcherObjectIndex.Shulker.DIRECTION, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectDirection>(DataWatcherObjectIndex.Shulker.DIRECTION, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 14) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 13) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Shulker.COLOR, 15) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	WITHER(NetworkEntityType.WITHER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.TARGET1, 50), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.TARGET2, 51), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.TARGET3, 52), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 49), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET1, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET1, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET1, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET2, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET2, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET2, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET3, 14) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.TARGET3, 13) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET3, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 15) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 14) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(NetworkEntityType.GUARDIAN, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 12) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 11) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Guardian.TARGET_ID, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Guardian.TARGET_ID, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Guardian.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8)
	),
	ELDER_GUARDIAN(NetworkEntityType.ELDER_GUARDIAN, SpecificRemapper.GUARDIAN),
	VINDICATOR(NetworkEntityType.VINDICATOR, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Vindicator.AGGRESSIVE, 12) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	EVOKER(NetworkEntityType.EVOKER, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Evoker.SPELL, 12) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	ILLUSIONER(NetworkEntityType.ILLUSIONER, SpecificRemapper.EVOKER),
	VEX(NetworkEntityType.VEX, SpecificRemapper.INSENTIENT,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Vex.FLAGS, 12) {}, ProtocolVersionsHelper.RANGE__1_11__1_12_2)
	),
	PARROT(NetworkEntityType.PARROT, SpecificRemapper.TAMEABLE,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Parrot.VARIANT, 2), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Parrot.VARIANT, 15) {}, ProtocolVersionsHelper.ALL_1_12)
	),
	ARMOR_STAND_MOB(NetworkEntityType.ARMOR_STAND_MOB, SpecificRemapper.ARMOR_STAND),
	BOAT(NetworkEntityType.BOAT,
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Boat.VARIANT, 4), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.LEFT_PADDLE, 13) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectFloatLe(object.getValue() ? Float.MAX_VALUE: 0f);
			}},ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, 14) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectFloatLe(object.getValue() ? Float.MAX_VALUE: 0f);
			}},ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 6) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 8) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 7) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Boat.VARIANT, 9) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.LEFT_PADDLE, 10) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2)
	),
	TNT(NetworkEntityType.TNT, SpecificRemapper.ENTITY,
		new Entry(new PeSimpleFlagAdder(
				new int[] {PeMetaBase.FLAG_IGNITED}, new boolean[] {true}
			), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Tnt.FUSE, 56), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Tnt.FUSE, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Tnt.FUSE, 5) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWBALL(NetworkEntityType.SNOWBALL, SpecificRemapper.ENTITY),
	EGG(NetworkEntityType.EGG, SpecificRemapper.ENTITY),
	FIREBALL(NetworkEntityType.FIREBALL, SpecificRemapper.ENTITY),
	FIRECHARGE(NetworkEntityType.FIRECHARGE, SpecificRemapper.ENTITY),
	ENDERPEARL(NetworkEntityType.ENDERPEARL, SpecificRemapper.ENTITY),
	WITHER_SKULL(NetworkEntityType.WITHER_SKULL, SpecificRemapper.FIREBALL,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.WitherSkull.CHARGED, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.WitherSkull.CHARGED, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.WitherSkull.CHARGED, 10), ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(NetworkEntityType.FALLING_OBJECT, SpecificRemapper.ENTITY),
	ENDEREYE(NetworkEntityType.ENDEREYE, SpecificRemapper.ENTITY),
	POTION(NetworkEntityType.POTION, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Potion.ITEM, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Potion.ITEM, 7) {}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Potion.ITEM, 5) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	EXP_BOTTLE(NetworkEntityType.EXP_BOTTLE, SpecificRemapper.ENTITY),
	LEASH_KNOT(NetworkEntityType.LEASH_KNOT, SpecificRemapper.ENTITY),
	FISHING_FLOAT(NetworkEntityType.FISHING_FLOAT, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.FinshingFloat.HOOKED_ENTITY, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.FinshingFloat.HOOKED_ENTITY, 5) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	ITEM(NetworkEntityType.ITEM, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Item.ITEM, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Item.ITEM, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Item.ITEM, 10) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(NetworkEntityType.MINECART, SpecificRemapper.ENTITY,
		//PE TODO: Damagetime and shake direction.
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK, 16), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, 17), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 18), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 6) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 8) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 7) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 9) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 8) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK, 20), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				int value = object.getValue();
				int id = value & 0xFFFF;
				int data = value >> 12;
				return new DataWatcherObjectInt((data << 16) | id);
			}
		}, ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK_Y, 10) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK_Y, 9) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 10) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART_CHEST(NetworkEntityType.MINECART_CHEST, SpecificRemapper.MINECART),
	MINECART_FURNACE(NetworkEntityType.MINECART_FURNACE, SpecificRemapper.MINECART,
		new Entry(new DataWatcherDataRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				//Simulate furnaceMinecart in Pocket.
				remapped.put(16, new DataWatcherObjectSVarInt(61));
				getObject(original, DataWatcherObjectIndex.MinecartFurnace.POWERED, DataWatcherObjectBoolean.class).ifPresent(boolWatcher -> {if(boolWatcher.getValue()) {
					remapped.put(16, new DataWatcherObjectSVarInt(62));
				}});
				remapped.put(17, new DataWatcherObjectSVarInt(6));
				remapped.put(18, new DataWatcherObjectByte((byte) 1));
			}
		}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.MinecartFurnace.POWERED, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.MinecartFurnace.POWERED, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART_TNT(NetworkEntityType.MINECART_TNT, SpecificRemapper.MINECART),
	MINECART_SPAWNER(NetworkEntityType.MINECART_MOB_SPAWNER, SpecificRemapper.MINECART),
	MINECART_HOPPER(NetworkEntityType.MINECART_HOPPER, SpecificRemapper.MINECART),
	MINECART_COMMAND(NetworkEntityType.MINECART_COMMAND, SpecificRemapper.MINECART,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.COMMAND, 71) {}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 72) {}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.COMMAND, 12) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.COMMAND, 11) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.COMMAND, 23) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8)),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 13) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 12) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectString>(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 24) {}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8))
	),
	ARROW(NetworkEntityType.ARROW, SpecificRemapper.ENTITY,
		new Entry(new PeFlagRemapper(DataWatcherObjectIndex.Arrow.CIRTICAL,
				new int[] {1}, new int[] {PeMetaBase.FLAG_CRITICAL}
		), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Arrow.CIRTICAL, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Arrow.CIRTICAL, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectByte>(DataWatcherObjectIndex.Arrow.CIRTICAL, 15) {}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(NetworkEntityType.SPECTRAL_ARROW, SpecificRemapper.ARROW),
	TIPPED_ARROW(NetworkEntityType.TIPPED_ARROW, SpecificRemapper.ARROW,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.TippedArrow.COLOR, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.TippedArrow.COLOR, 6) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	FIREWORK(NetworkEntityType.FIREWORK, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Firework.ITEM, 16) {}, ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Firework.ITEM, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Firework.ITEM, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.Firework.ITEM, 8) {}, ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Firework.USER, 7) {}, ProtocolVersionsHelper.RANGE__1_11_1__1_12_2)
	),
	ITEM_FRAME(NetworkEntityType.ITEM_FRAME, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.ItemFrame.ITEM, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.ItemFrame.ITEM, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.ItemFrame.ITEM, 8) {}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectItemStack>(DataWatcherObjectIndex.ItemFrame.ITEM, 2) {}, ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.ItemFrame.ROTATION, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.ItemFrame.ROTATION, 6) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.ItemFrame.ROTATION, 9), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.ItemFrame.ROTATION, 3) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(NetworkEntityType.ENDER_CRYSTAL, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.EnderCrystal.TARGET, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectOptionalPosition>(DataWatcherObjectIndex.EnderCrystal.TARGET, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 6) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	ARMOR_STAND_OBJECT(NetworkEntityType.ARMOR_STAND_OBJECT, SpecificRemapper.ARMOR_STAND),
	AREA_EFFECT_CLOUD(NetworkEntityType.AREA_EFFECT_CLOUD, SpecificRemapper.ENTITY,
		new Entry(new IndexValueRemapperNumberToFloatLe(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 61), ProtocolVersion.MINECRAFT_PE),
		//TODO: area effectcloud waiting?
		new Entry(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 63), ProtocolVersion.MINECRAFT_PE),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 6) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectFloat>(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 5) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 7) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 6) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 8) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectBoolean>(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 7) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 9) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 8) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA1, 10) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA1, 9) {}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA2, 11) {}, ProtocolVersionsHelper.RANGE__1_10__1_12_2),
		new Entry(new IndexValueRemapperNoOp<DataWatcherObjectVarInt>(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE_DATA2, 10) {}, ProtocolVersionsHelper.ALL_1_9)
	),
	SHULKER_BULLET(NetworkEntityType.SHULKER_BULLET, SpecificRemapper.ENTITY),
	LAMA_SPIT(NetworkEntityType.LAMA_SPIT, SpecificRemapper.ENTITY),
	DRAGON_FIREBALL(NetworkEntityType.DRAGON_FIREBALL, SpecificRemapper.ENTITY),
	EVOCATOR_FANGS(NetworkEntityType.EVOCATOR_FANGS, SpecificRemapper.ENTITY);

	private static final EnumMap<NetworkEntityType, SpecificRemapper> wtype = CollectionsUtils.makeEnumMappingEnumMap(SpecificRemapper.class, NetworkEntityType.class, (e -> e.type));

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
