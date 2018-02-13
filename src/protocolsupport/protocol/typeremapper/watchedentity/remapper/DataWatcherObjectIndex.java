package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.HashMap;

public class DataWatcherObjectIndex {

	public static class Entity {
		public static final int FLAGS = takeNextId();
		public static final int AIR = takeNextId();
		public static final int NAMETAG = takeNextId();
		public static final int NAMETAG_VISIBLE = takeNextId();
		public static final int SILENT = takeNextId();
		public static final int NO_GRAVITY = takeNextId();
		//Lead - special hack for PE lead.
		public static final int LEAD = 38;
	}

	public static class EntityLiving extends Entity {
		public static final int HAND_USE = takeNextId();
		public static final int HEALTH = takeNextId();
		public static final int POTION_COLOR = takeNextId();
		public static final int POTION_AMBIENT = takeNextId();
		public static final int ARROWS_IN = takeNextId();
	}

	public static class Insentient extends EntityLiving {
		public static final int NO_AI = takeNextId();
	}

	public static final class Player extends EntityLiving {
		public static final int ADDITIONAL_HEARTS = takeNextId();
		public static final int SCORE = takeNextId();
		public static final int SKIN_FLAGS = takeNextId();
		public static final int MAIN_HAND = takeNextId();
		public static final int LEFT_SHOULDER_ENTITY = takeNextId();
		public static final int RIGHT_SHOULDER_ENTITY = takeNextId();
	}

	public static class Ageable extends Insentient {
		public static final int AGE = takeNextId();
		//age - special hack for hologram plugins that want to set int age
		public static final int AGE_HACK = 30;
	}

	public static class Tameable extends Ageable {
		public static final int TAME_FLAGS = takeNextId();
		public static final int OWNER = takeNextId();
	}

	public static class ArmorStand extends EntityLiving {
		public static final int FLAGS = takeNextId();
		public static final int HEAD_ROT = takeNextId();
		public static final int BODY_ROT = takeNextId();
		public static final int LEFT_ARM_ROT = takeNextId();
		public static final int RIGHT_ARM_ROT = takeNextId();
		public static final int LEFT_LEG_ROT = takeNextId();
		public static final int RIGHT_LEG_ROT = takeNextId();
	}

	public static class BaseHorse extends Ageable {
		public static final int FLAGS = takeNextId();
		public static final int OWNER = takeNextId();
	}

	public static class BattleHorse extends BaseHorse {
		public static final int VARIANT = takeNextId();
		public static final int ARMOR = takeNextId();
	}

	public static class CargoHorse extends BaseHorse {
		public static final int HAS_CHEST = takeNextId();
	}

	public static class Lama extends CargoHorse {
		public static final int STRENGTH = takeNextId();
		public static final int CARPET_COLOR = takeNextId();
		public static final int VARIANT = takeNextId();
	}

	public static class Bat extends Insentient {
		public static final int HANGING = takeNextId();
	}

	public static class Ocelot extends Tameable {
		public static final int VARIANT = takeNextId();
	}

	public static class Wolf extends Tameable {
		public static final int HEALTH = takeNextId();
		public static final int BEGGING = takeNextId();
		public static final int COLLAR_COLOR = takeNextId();
	}

	public static class Pig extends Ageable {
		public static final int HAS_SADLLE = takeNextId();
		public static final int BOOST_TIME = takeNextId();
	}

	public static class Rabbit extends Ageable {
		public static final int VARIANT = takeNextId();
	}

	public static class Sheep extends Ageable {
		public static final int FLAGS = takeNextId();
	}

	public static class PolarBear extends Ageable  {
		public static final int STANDING_UP = takeNextId();
	}

	public static class Villager extends Ageable  {
		public static final int PROFESSION = takeNextId();
	}

	public static class Enderman extends Insentient {
		public static final int CARRIED_BLOCK = takeNextId();
		public static final int SCREAMING = takeNextId();
	}

	public static class EnderDragon extends Insentient {
		public static final int PHASE = takeNextId();
	}

	public static class Snowman extends Insentient {
		public static final int NO_HAT = takeNextId();
	}

	public static class Zombie extends Insentient {
		public static final int BABY = takeNextId();
		public static final int PROFESSION = takeNextId();
		public static final int HANDS_UP = takeNextId();
	}

	public static class ZombieVillager extends Zombie {
		public static final int CONVERTING = takeNextId();
	}

	public static class Blaze extends Insentient {
		public static final int ON_FIRE = takeNextId();
	}

	public static class Spider extends Insentient {
		public static final int CLIMBING = takeNextId();
	}

	public static class Creeper extends Insentient {
		public static final int STATE = takeNextId();
		public static final int POWERED = takeNextId();
		public static final int IGNITED = takeNextId();
	}

	public static class Ghast extends Insentient {
		public static final int ATTACKING = takeNextId();
	}

	public static class Slime extends Insentient {
		public static final int SIZE = takeNextId();
	}

	public static class Skeleton extends Insentient {
		public static final int ATTACKING = takeNextId();
	}

	public static class Witch extends Insentient {
		public static final int AGGRESSIVE = takeNextId();
	}

	public static class IronGolem extends Insentient{
		public static final int PLAYER_CREATED = takeNextId();
	}

	public static class Shulker extends Insentient {
		public static final int DIRECTION = takeNextId();
		public static final int ATTACHMENT_POS = takeNextId();
		public static final int SHIELD_HEIGHT = takeNextId();
		public static final int COLOR = takeNextId();
	}

	public static class Wither extends Insentient {
		public static final int TARGET1 = takeNextId();
		public static final int TARGET2 = takeNextId();
		public static final int TARGET3 = takeNextId();
		public static final int INVULNERABLE_TIME = takeNextId();
	}

	public static class Guardian extends Insentient {
		public static final int SPIKES = takeNextId();
		public static final int TARGET_ID = takeNextId();
	}

	public static class Vindicator extends Insentient {
		public static final int AGGRESSIVE = takeNextId();
	}

	public static class Evoker extends Insentient {
		public static final int SPELL = takeNextId();
	}

	public static class Vex extends Insentient {
		public static final int FLAGS = takeNextId();
	}

	public static class Parrot extends Tameable {
		public static final int VARIANT = takeNextId();
	}

	public static class Boat extends Entity {
		public static final int TIME_SINCE_LAST_HIT = takeNextId();
		public static final int FORWARD_DIRECTION = takeNextId();
		public static final int DAMAGE_TAKEN = takeNextId();
		public static final int VARIANT = takeNextId();
		public static final int LEFT_PADDLE = takeNextId();
		public static final int RIGHT_PADDLE = takeNextId();
	}

	public static class Tnt extends Entity {
		public static final int FUSE = takeNextId();
	}

	public static class WitherSkull extends Entity {
		public static final int CHARGED = takeNextId();
	}

	public static class Potion extends Entity {
		public static final int ITEM = takeNextId();
	}

	public static class FinshingFloat extends Entity {
		public static final int HOOKED_ENTITY = takeNextId();
	}

	public static class Item extends Entity {
		public static final int ITEM = takeNextId();
	}

	public static class Minecart extends Entity {
		public static final int SHAKING_POWER = takeNextId();
		public static final int SHAKING_DIRECTION = takeNextId();
		public static final int DAMAGE_TAKEN = takeNextId();
		public static final int BLOCK = takeNextId();
		public static final int BLOCK_Y = takeNextId();
		public static final int SHOW_BLOCK = takeNextId();
	}

	public static class MinecartFurnace extends Minecart {
		public static final int POWERED = takeNextId();
	}

	public static class MinecartCommand extends Minecart {
		public static final int COMMAND = takeNextId();
		public static final int LAST_OUTPUT = takeNextId();
	}

	public static class Arrow extends Entity {
		public static final int CIRTICAL = takeNextId();
	}

	public static class TippedArrow extends Arrow {
		public static final int COLOR = takeNextId();
	}

	public static class Firework extends Entity {
		public static final int ITEM = takeNextId();
		public static final int USER = takeNextId();
	}

	public static class ItemFrame extends Entity {
		public static final int ITEM = takeNextId();
		public static final int ROTATION = takeNextId();
	}

	public static class EnderCrystal extends Entity {
		public static final int TARGET = takeNextId();
		public static final int SHOW_BOTTOM = takeNextId();
	}

	public static class AreaEffectCloud extends Entity {
		public static final int RADIUS = takeNextId();
		public static final int COLOR = takeNextId();
		public static final int SINGLE_POINT = takeNextId();
		public static final int PARTICLE = takeNextId();
		public static final int PARTICLE_DATA1 = takeNextId();
		public static final int PARTICLE_DATA2 = takeNextId();
	}

	private static final HashMap<Class<?>, Integer> lastTakenId = new HashMap<>();
	protected static int takeNextId() {
		try {
			Class<?> caller = Class.forName(new Exception().getStackTrace()[1].getClassName());
			int newid = -1;
			if (lastTakenId.containsKey(caller)) {
				newid = lastTakenId.get(caller) + 1;
			} else {
				Class<?> superclass = caller.getSuperclass();
				newid = superclass == Object.class ? 0 : lastTakenId.get(superclass) + 1;
			}
			lastTakenId.put(caller, newid);
			return newid;
		} catch (Exception e) {
			throw new RuntimeException("Exception occured while computing datawatcherobjectindex", e);
		}
	}

}
