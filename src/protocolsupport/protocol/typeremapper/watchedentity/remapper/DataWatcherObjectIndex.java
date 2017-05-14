package protocolsupport.protocol.typeremapper.watchedentity.remapper;

//TODO: build a hierarchy to auto calculate id
public class DataWatcherObjectIndex {

	public static final class Entity {
		public static final int FLAGS = 0;
		public static final int AIR = 1;
		public static final int NAMETAG = 2;
		public static final int NAMETAG_VISIBLE = 3;
		public static final int SILENT = 4;
		public static final int NO_GRAVITY = 5;
	}

	public static final class EntityLiving {
		public static final int HAND_USE = 6;
		public static final int HEALTH = 7;
		public static final int POTION_COLOR = 8;
		public static final int POTION_AMBIENT = 9;
		public static final int ARROWS_IN = 10;
	}

	public static final class Insentient {
		public static final int NO_AI = 11;
	}

	public static final class Player {
		public static final int ADDITIONAL_HEARTS = 11;
		public static final int SCORE = 12;
		public static final int SKIN_FLAGS = 13;
		public static final int MAIN_HAND = 14;
		public static final int LEFT_SHOULDER_ENTITY = 15;
		public static final int RIGHT_SHOULDER_ENTITY = 16;
	}

	public static final class Ageable {
		public static final int AGE = 12;
		//age - special hack for hologram plugins that want to set int age
		public static final int AGE_HACK = 30;
	}

	public static final class Tameable {
		public static final int TAME_FLAGS = 13;
	}

	public static final class ArmorStand {
		public static final int FLAGS = 11;
		public static final int HEAD_ROT = 12;
		public static final int BODY_ROT = 13;
		public static final int LEFT_ARM_ROT = 14;
		public static final int RIGHT_ARM_ROT = 15;
		public static final int LEFT_LEG_ROT = 16;
		public static final int RIGHT_LEG_ROT = 17;
	}

	public static final class BaseHorse {
		public static final int FLAGS = 13;
	}

	public static final class BattleHorse {
		public static final int VARIANT = 15;
		public static final int ARMOR = 16;
	}

	public static final class CargoHorse {
		public static final int HAS_CHEST = 15;
	}

	public static final class Lama {
		public static final int STRENGTH = 16;
		public static final int CARPET_COLOR = 17;
		public static final int VARINAT = 18;
	}

	public static final class Bat {
		public static final int HANGING = 12;
	}

	public static final class Ocelot {
		public static final int VARIANT = 15;
	}

	public static final class Wolf {
		public static final int HEALTH = 15;
		public static final int BEGGING = 16;
		public static final int COLLAR_COLOR = 17;
	}

	public static final class Pig {
		public static final int HAS_SADLLE = 13;
		public static final int BOOST_TIME = 14;
	}

	public static final class Rabbit {
		public static final int VARIANT = 13;
	}

	public static final class Sheep {
		public static final int FLAGS = 13;
	}

	public static final class PolarBear {
		public static final int STANDING_UP = 13;
	}

	public static final class Villager {
		public static final int PROFESSION = 13;
	}

	public static final class Enderman {
		public static final int CARRIED_BLOCK = 12;
		public static final int SCREAMING = 13;
	}

	public static final class EnderDragon {
		public static final int PHASE = 12;
	}

	public static final class Snowman {
		public static final int NO_HAT = 12;
	}

	public static final class Zombie {
		public static final int BABY = 12;
		public static final int PROFESSION = 13;
		public static final int HANDS_UP = 14;
	}

	public static final class ZombieVillager {
		public static final int CONVERTING = 15;
	}

	public static final class Blaze {
		public static final int ON_FIRE = 12;
	}

	public static final class Spider {
		public static final int CLIMBING = 12;
	}

	public static final class Creeper {
		public static final int STATE = 12;
		public static final int POWERED = 13;
		public static final int IGNITED = 14;
	}

	public static final class Ghast {
		public static final int ATTACKING = 12;
	}

	public static final class Slime {
		public static final int SIZE = 12; 
	}

	public static final class BaseSkeleton {
		public static final int ATTACKING = 12;
	}

	public static final class Witch {
		public static final int AGGRESSIVE = 12;
	}

	public static final class IronGolem {
		public static final int PLAYER_CREATED = 12;
	}

	public static final class Shulker {
		public static final int DIRECTION = 12;
		public static final int ATTACHMENT_POS = 13;
		public static final int SHIELD_HEIGHT = 14;
		public static final int COLOR = 15;
	}

	public static final class Wither {
		public static final int TARGET1 = 12;
		public static final int TARGET2 = 13;
		public static final int TARGET3 = 14;
		public static final int INVULNERABLE_TIME = 15;
	}

	public static final class Guardian {
		public static final int SPIKES = 12;
		public static final int TARGET_ID = 13;
	}

	public static final class Vindicator {
		public static final int AGGRESSIVE = 12;
	}

	public static final class Evoker {
		public static final int SPELL = 12;
	}

	public static final class Vex {
		public static final int FLAGS = 12;
	}

	public static final class Boat {
		public static final int TIME_SINCE_LAST_HIT = 6;
		public static final int FORWARD_DIRECTION = 7;
		public static final int DAMAGE_TAKEN = 8;
		public static final int VARIANT = 9;
		public static final int LEFT_PADDLE = 10;
		public static final int RIGHT_PADDLE = 11;
	}

	public static final class Tnt {
		public static final int FUSE = 6;
	}

	public static final class WitherSkull {
		public static final int CHARGED = 6;
	}

	public static final class Potion {
		public static final int ITEM = 6;
	}

	public static final class FinshingFloat {
		public static final int HOOKED_ENTITY = 6;
	}

	public static final class Item {
		public static final int ITEM = 6;
	}

	public static final class Minecart {
		public static final int SHAKING_POWER = 6;
		public static final int SHAKING_DIRECTION = 7;
		public static final int DAMAGE_TAKEN = 8;
		public static final int BLOCK = 9;
		public static final int BLOCK_Y = 10;
		public static final int SHOW_BLOCK = 11;
		public static final int DATA12 = 12;
		public static final int DATA13 = 13;
	}

	public static final class Arrow {
		public static final int CIRTICAL = 6;
	}

	public static final class TippedArrow {
		public static final int COLOR = 7;
	}

	public static final class Firework {
		public static final int ITEM = 6;
		public static final int USER = 7;
	}

	public static final class ItemFrame {
		public static final int ITEM = 6;
		public static final int ROTATION = 7;
	}

	public static final class EnderCrystal {
		public static final int TARGET = 6;
		public static final int SHOW_BOTTOM = 7;
	}

	public static final class AreaEffectCloud {
		public static final int RADIUS = 6;
		public static final int COLOR = 7;
		public static final int SINGLE_POINT = 8;
		public static final int PARTICLE = 9;
		public static final int PARTICLE_DATA1 = 10;
		public static final int PARTICLE_DATA2 = 11;
	}

}
