package protocolsupport.protocol.utils.datawatcher;

import java.util.HashMap;
import java.util.Optional;

import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockData;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNBTTagCompound;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectParticle;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class DataWatcherObjectIndex<T extends DataWatcherObject<?>> {

	protected int index;
	protected Class<T> expectedDWObjectType;
	protected DataWatcherObjectIndex(int index, Class<T> expectedType) {
		this.index = index;
		this.expectedDWObjectType = expectedType;
	}

	public Optional<T> getValue(ArrayMap<DataWatcherObject<?>> metadata) {
		DataWatcherObject<?> object = metadata.get(index);
		if (expectedDWObjectType.isInstance(object)) {
			return Optional.of(expectedDWObjectType.cast(object));
		}
		return Optional.empty();
	}

	public static class Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> FLAGS = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> AIR = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectOptionalChat> NAMETAG = takeNextIndex(DataWatcherObjectOptionalChat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> NAMETAG_VISIBLE = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SILENT = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> NO_GRAVITY = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class EntityLiving extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> HAND_USE = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> HEALTH = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> POTION_COLOR = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> POTION_AMBIENT = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> ARROWS_IN = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Insentient extends EntityLiving {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> FLAGS = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static final class Player extends EntityLiving {
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> ADDITIONAL_HEARTS = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SCORE = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> SKIN_FLAGS = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> MAIN_HAND = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectNBTTagCompound> LEFT_SHOULDER_ENTITY = takeNextIndex(DataWatcherObjectNBTTagCompound.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectNBTTagCompound> RIGHT_SHOULDER_ENTITY = takeNextIndex(DataWatcherObjectNBTTagCompound.class);
	}

	public static class Ageable extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> IS_BABY = takeNextIndex(DataWatcherObjectBoolean.class);
		//age - special hack for hologram plugins that want to set int age
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> AGE_HACK = new DataWatcherObjectIndex<>(30, DataWatcherObjectVarInt.class);
	}

	public static class Tameable extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> TAME_FLAGS = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectOptionalUUID> OWNER = takeNextIndex(DataWatcherObjectOptionalUUID.class);
	}

	public static class ArmorStand extends EntityLiving {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> FLAGS = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> HEAD_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> BODY_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> LEFT_ARM_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> RIGHT_ARM_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> LEFT_LEG_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVector3f> RIGHT_LEG_ROT = takeNextIndex(DataWatcherObjectVector3f.class);
	}

	public static class BaseHorse extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> FLAGS = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectOptionalUUID> OWNER = takeNextIndex(DataWatcherObjectOptionalUUID.class);
	}

	public static class BattleHorse extends BaseHorse {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> ARMOR = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class CargoHorse extends BaseHorse {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> HAS_CHEST = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Lama extends CargoHorse {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> STRENGTH = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> CARPET_COLOR = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Bat extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> HANGING = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Ocelot extends Tameable {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Wolf extends Tameable {
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> DAMAGE_TAKEN = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> BEGGING = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> COLLAR_COLOR = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Pig extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> HAS_SADLLE = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> BOOST_TIME = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Rabbit extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Sheep extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> FLAGS = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class PolarBear extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> STANDING_UP = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Villager extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> PROFESSION = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Enderman extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBlockData> CARRIED_BLOCK = takeNextIndex(DataWatcherObjectBlockData.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SCREAMING = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class EnderDragon extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> PHASE = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Snowman extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> NO_HAT = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Zombie extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> BABY = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> UNUSED = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> HANDS_UP = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class ZombieVillager extends Zombie {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> CONVERTING = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> PROFESSION = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Blaze extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> ON_FIRE = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Spider extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> CLIMBING = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Creeper extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> STATE = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> POWERED = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> IGNITED = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Ghast extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> ATTACKING = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Slime extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SIZE = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Skeleton extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SWINGING_HANDS = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Witch extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> DRINKING_POTION = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class IronGolem extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> PLAYER_CREATED = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Shulker extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectDirection> DIRECTION = takeNextIndex(DataWatcherObjectDirection.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectOptionalPosition> ATTACHMENT_POS = takeNextIndex(DataWatcherObjectOptionalPosition.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> SHIELD_HEIGHT = takeNextIndex(DataWatcherObjectByte.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> COLOR = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Wither extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> TARGET1 = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> TARGET2 = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> TARGET3 = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> INVULNERABLE_TIME = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Guardian extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SPIKES = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> TARGET_ID = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Vindicator extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> HAS_TARGET = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Evoker extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> SPELL = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Vex extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> ATTACK_MODE = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class Parrot extends Tameable {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Phantom extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SIZE = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class BaseFish extends Insentient {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> FROM_BUCKET = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class PufferFish extends BaseFish {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> PUFF_STATE = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class TropicalFish extends BaseFish {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Turtle extends Ageable {
		public static final DataWatcherObjectIndex<DataWatcherObjectPosition> HOME_POS = takeNextIndex(DataWatcherObjectPosition.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> HAS_EGG = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> LAYING_EGG = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectPosition> TRAVEL_POS = takeNextIndex(DataWatcherObjectPosition.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> GOING_HOME = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> TRAVELING = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Drowned extends Zombie {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> HAS_TARGET = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Boat extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> TIME_SINCE_LAST_HIT = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> FORWARD_DIRECTION = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> DAMAGE_TAKEN = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> VARIANT = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> LEFT_PADDLE = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> RIGHT_PADDLE = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SPLASH_TIMER = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Tnt extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> FUSE = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class WitherSkull extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> CHARGED = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class Potion extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectItemStack> ITEM = takeNextIndex(DataWatcherObjectItemStack.class);
	}

	public static class FishingFloat extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> HOOKED_ENTITY = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Item extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectItemStack> ITEM = takeNextIndex(DataWatcherObjectItemStack.class);
	}

	public static class Minecart extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SHAKING_POWER = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> SHAKING_DIRECTION = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> DAMAGE_TAKEN = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> BLOCK = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> BLOCK_Y = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SHOW_BLOCK = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class MinecartFurnace extends Minecart {
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> POWERED = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class MinecartCommand extends Minecart {
		public static final DataWatcherObjectIndex<DataWatcherObjectString> COMMAND = takeNextIndex(DataWatcherObjectString.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectChat> LAST_OUTPUT = takeNextIndex(DataWatcherObjectChat.class);
	}

	public static class Arrow extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectByte> CIRTICAL = takeNextIndex(DataWatcherObjectByte.class);
	}

	public static class TippedArrow extends Arrow {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> COLOR = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Trident extends Arrow {
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> LOYALTY = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class Firework extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectItemStack> ITEM = takeNextIndex(DataWatcherObjectItemStack.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> USER = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class ItemFrame extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectItemStack> ITEM = takeNextIndex(DataWatcherObjectItemStack.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> ROTATION = takeNextIndex(DataWatcherObjectVarInt.class);
	}

	public static class EnderCrystal extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectOptionalPosition> TARGET = takeNextIndex(DataWatcherObjectOptionalPosition.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SHOW_BOTTOM = takeNextIndex(DataWatcherObjectBoolean.class);
	}

	public static class AreaEffectCloud extends Entity {
		public static final DataWatcherObjectIndex<DataWatcherObjectFloat> RADIUS = takeNextIndex(DataWatcherObjectFloat.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectVarInt> COLOR = takeNextIndex(DataWatcherObjectVarInt.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectBoolean> SINGLE_POINT = takeNextIndex(DataWatcherObjectBoolean.class);
		public static final DataWatcherObjectIndex<DataWatcherObjectParticle> PARTICLE = takeNextIndex(DataWatcherObjectParticle.class);
	}

	protected static final HashMap<Class<?>, Integer> lastTakenId = new HashMap<>();
	protected static <T, TDW extends DataWatcherObject<T>> DataWatcherObjectIndex<TDW> takeNextIndex(Class<TDW> expectedType) {
		try {
			Class<?> caller = Class.forName(new Exception().getStackTrace()[1].getClassName());
			int newIndex = -1;
			if (lastTakenId.containsKey(caller)) {
				newIndex = lastTakenId.get(caller) + 1;
			} else {
				Class<?> superclass = caller.getSuperclass();
				newIndex = superclass == Object.class ? 0 : lastTakenId.get(superclass) + 1;
			}
			lastTakenId.put(caller, newIndex);
			return new DataWatcherObjectIndex<>(newIndex, expectedType);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while computing datawatcherobjectindex", e);
		}
	}

}