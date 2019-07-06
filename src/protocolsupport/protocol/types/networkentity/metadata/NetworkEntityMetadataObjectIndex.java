package protocolsupport.protocol.types.networkentity.metadata;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Optional;

import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBTTagCompound;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityMetadataObjectIndex<T extends NetworkEntityMetadataObject<?>> {

	protected final Class<?> entityClass;
	protected final int index;
	protected final Class<T> expectedDWObjectType;
	protected NetworkEntityMetadataObjectIndex(Class<?> entityClass, int index, Class<T> expectedType) {
		this.entityClass = entityClass;
		this.index = index;
		this.expectedDWObjectType = expectedType;
	}

	public Optional<T> getValue(ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
		NetworkEntityMetadataObject<?> object = metadata.get(index);
		if (object == null) {
			return Optional.empty();
		}
		if (expectedDWObjectType.isInstance(object)) {
			return Optional.of(expectedDWObjectType.cast(object));
		}
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logWarning(MessageFormat.format(
				"Wrong metadata type for entity type {0} index {1}, expected {2}, got {3}",
				entityClass.getSimpleName(), index, expectedDWObjectType.getName(), object.getClass().getName()
			));
		}
		return Optional.empty();
	}

	public static class Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> AIR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalChat> NAMETAG = takeNextIndex(NetworkEntityMetadataObjectOptionalChat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> NAMETAG_VISIBLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SILENT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> NO_GRAVITY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectEntityPose> POSE = takeNextIndex(NetworkEntityMetadataObjectEntityPose.class);
	}

	public static class EntityLiving extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HAND_USE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> HEALTH = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> POTION_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POTION_AMBIENT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ARROWS_IN = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> BED_LOCATION = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
	}

	public static class Insentient extends EntityLiving {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final int FLAGS_BIT_ATTACKING = 2;
	}

	public static final class Player extends EntityLiving {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> ADDITIONAL_HEARTS = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SCORE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SKIN_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> MAIN_HAND = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectNBTTagCompound> LEFT_SHOULDER_ENTITY = takeNextIndex(NetworkEntityMetadataObjectNBTTagCompound.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectNBTTagCompound> RIGHT_SHOULDER_ENTITY = takeNextIndex(NetworkEntityMetadataObjectNBTTagCompound.class);
	}

	public static class Ageable extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IS_BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		//age - special hack for hologram plugins that want to set int age
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> AGE_HACK = new NetworkEntityMetadataObjectIndex<>(Ageable.class, 30, NetworkEntityMetadataObjectVarInt.class);
	}

	public static class RaidParticipant extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CELEBRATING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Tameable extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> TAME_FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> OWNER = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
	}

	public static class ArmorStand extends EntityLiving {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> HEAD_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> BODY_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> LEFT_ARM_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> RIGHT_ARM_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> LEFT_LEG_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVector3f> RIGHT_LEG_ROT = takeNextIndex(NetworkEntityMetadataObjectVector3f.class);
	}

	public static class BaseHorse extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> OWNER = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
	}

	public static class BattleHorse extends BaseHorse {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ARMOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class CargoHorse extends BaseHorse {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_CHEST = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Lama extends CargoHorse {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> STRENGTH = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> CARPET_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Bat extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HANGING = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Wolf extends Tameable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> DAMAGE_TAKEN = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> BEGGING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLLAR_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Cat extends Tameable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> UNKNOWN_1 = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> UNKNOWN_2 = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLLAR_COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Ocelot extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> TRUSTING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Pig extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_SADLLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BOOST_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Rabbit extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Sheep extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class PolarBear extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> STANDING_UP = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class AbstractMerchant extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> HEAD_SHAKE_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Villager extends AbstractMerchant {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> VDATA = takeNextIndex(NetworkEntityMetadataObjectVillagerData.class);
	}

	public static class Fox extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> UNKNOWN_1 = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> UNKNOWN_2 = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
	}

	public static class Panda extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BREED_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SNEEZE_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> EAT_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> GENE_MAIN = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> GENE_HIDDEN = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> FLAGS = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Turtle extends Ageable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectPosition> HOME_POS = takeNextIndex(NetworkEntityMetadataObjectPosition.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> HAS_EGG = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> LAYING_EGG = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectPosition> TRAVEL_POS = takeNextIndex(NetworkEntityMetadataObjectPosition.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> GOING_HOME = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> TRAVELING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Enderman extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBlockData> CARRIED_BLOCK = takeNextIndex(NetworkEntityMetadataObjectBlockData.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SCREAMING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class EnderDragon extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> PHASE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Snowman extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> NO_HAT = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Zombie extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> BABY = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> UNUSED = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DROWNING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ZombieVillager extends Zombie {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CONVERTING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> VDATA = takeNextIndex(NetworkEntityMetadataObjectVillagerData.class);
	}

	public static class Blaze extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> ON_FIRE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Spider extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> CLIMBING = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Creeper extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> STATE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POWERED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> IGNITED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Ghast extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> ATTACKING = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Slime extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SIZE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class IronGolem extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> PLAYER_CREATED = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Shulker extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectDirection> DIRECTION = takeNextIndex(NetworkEntityMetadataObjectDirection.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> ATTACHMENT_POS = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SHIELD_HEIGHT = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> COLOR = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Wither extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET1 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET2 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET3 = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> INVULNERABLE_TIME = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Guardian extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SPIKES = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TARGET_ID = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Vex extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> ATTACK_MODE = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Parrot extends Tameable {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Phantom extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SIZE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class BaseFish extends Insentient {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> FROM_BUCKET = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class PufferFish extends BaseFish {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> PUFF_STATE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class TropicalFish extends BaseFish {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Witch extends RaidParticipant {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> DRINKING_POTION = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Vindicator extends RaidParticipant {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> HAS_TARGET = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Evoker extends RaidParticipant {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> SPELL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Pillager extends RaidParticipant {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> USING_CROSSBOW = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Boat extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> TIME_SINCE_LAST_HIT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> FORWARD_DIRECTION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> DAMAGE_TAKEN = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> VARIANT = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> LEFT_PADDLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> RIGHT_PADDLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SPLASH_TIMER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Tnt extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> FUSE = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class WitherSkull extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> CHARGED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class Potion extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
	}

	public static class FishingFloat extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> HOOKED_ENTITY = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Item extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
	}

	public static class Minecart extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SHAKING_POWER = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> SHAKING_DIRECTION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> DAMAGE_TAKEN = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BLOCK = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> BLOCK_Y = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOW_BLOCK = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class MinecartFurnace extends Minecart {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> POWERED = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class MinecartCommand extends Minecart {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectString> COMMAND = takeNextIndex(NetworkEntityMetadataObjectString.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectChat> LAST_OUTPUT = takeNextIndex(NetworkEntityMetadataObjectChat.class);
	}

	public static class Arrow extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> CIRTICAL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> SHOOTER = takeNextIndex(NetworkEntityMetadataObjectOptionalUUID.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> PIERCING_LEVEL = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class TippedArrow extends Arrow {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class Trident extends Arrow {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> LOYALTY = takeNextIndex(NetworkEntityMetadataObjectByte.class);
	}

	public static class Firework extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalVarInt> USER = takeNextIndex(NetworkEntityMetadataObjectOptionalVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOT_AT_ANGLE = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class ItemFrame extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectItemStack> ITEM = takeNextIndex(NetworkEntityMetadataObjectItemStack.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> ROTATION = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
	}

	public static class EnderCrystal extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalPosition> TARGET = takeNextIndex(NetworkEntityMetadataObjectOptionalPosition.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SHOW_BOTTOM = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
	}

	public static class AreaEffectCloud extends Entity {
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectFloat> RADIUS = takeNextIndex(NetworkEntityMetadataObjectFloat.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> COLOR = takeNextIndex(NetworkEntityMetadataObjectVarInt.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> SINGLE_POINT = takeNextIndex(NetworkEntityMetadataObjectBoolean.class);
		public static final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectParticle> PARTICLE = takeNextIndex(NetworkEntityMetadataObjectParticle.class);
	}

	protected static final HashMap<Class<?>, Integer> lastTakenId = new HashMap<>();
	protected static <T, TDW extends NetworkEntityMetadataObject<T>> NetworkEntityMetadataObjectIndex<TDW> takeNextIndex(Class<TDW> expectedType) {
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
			return new NetworkEntityMetadataObjectIndex<>(caller, newIndex, expectedType);
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while computing datawatcherobjectindex", e);
		}
	}

}