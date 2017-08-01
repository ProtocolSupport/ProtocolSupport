package protocolsupport.protocol.utils.types;

public enum Particle {

	EXPLOSION_NORMAL("explode"),
	EXPLOSION_LARGE("largeexplode"),
	EXPLOSION_HUGE("hugeexplosion"),
	FIREWORKS_SPARK("fireworksSpark"),
	WATER_BUBBLE("bubble"),
	WATER_SPLASH("splash"),
	WATER_WAKE("wake"),
	SUSPENDED("suspended"),
	SUSPENDED_DEPTH("depthsuspend"),
	CRIT("crit"),
	CRIT_MAGIC("magicCrit"),
	SMOKE_NORMAL("smoke"),
	SMOKE_LARGE("largesmoke"),
	SPELL("spell"),
	SPELL_INSTANT("instantSpell"),
	SPELL_MOB("mobSpell"),
	SPELL_MOB_AMBIENT("mobSpellAmbient"),
	SPELL_WITCH("witchMagic"),
	DRIP_WATER("dripWater"),
	DRIP_LAVA("dripLava"),
	VILLAGER_ANGRY("angryVillager"),
	VILLAGER_HAPPY("happyVillager"),
	TOWN_AURA("townaura"),
	NOTE("note"),
	PORTAL("portal"),
	ENCHANTMENT_TABLE("enchantmenttable"),
	FLAME("flame"),
	LAVA("lava"),
	FOOTSTEP("footstep"),
	CLOUD("cloud"),
	REDSTONE("reddust"),
	SNOWBALL("snowballpoof"),
	SNOW_SHOVEL("snowshovel"),
	SLIME("slime"),
	HEART("heart"),
	BARRIER("barrier"),
	ITEM_CRACK("iconcrack"),
	BLOCK_CRACK("blockcrack"),
	BLOCK_DUST("blockdust"),
	WATER_DROP("droplet"),
	ITEM_TAKE("take"),
	MOB_APPEARANCE("mobappearance"),
	DRAGON_BREATH("dragonbreath"),
	END_ROD("endRod"),
	DAMAGE_INDICATOR("damageIndicator"),
	SWEEP_ATTACK("sweepAttack"),
	FALLING_DUST("fallingdust"),
    TOTEM("totem"),
    SPIT("spit");

	private final String id;

	private Particle(final String id) {
		this.id = id;
	}

	public String getName() {
		return id;
	}

	public int getId() {
		return ordinal();
	}

	public static Particle getById(int id) {
		return Particle.values()[id];
	}

}
