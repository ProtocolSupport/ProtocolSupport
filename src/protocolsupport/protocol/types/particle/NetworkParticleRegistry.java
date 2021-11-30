package protocolsupport.protocol.types.particle;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.bukkit.Particle;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.types.particle.types.NetworkParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleAngryVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleAsh;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlock;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlockMarker;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubble;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubbleColumnUp;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubblePop;
import protocolsupport.protocol.types.particle.types.NetworkParticleCampfireCozySmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleCampfireSignalSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleCloud;
import protocolsupport.protocol.types.particle.types.NetworkParticleComposter;
import protocolsupport.protocol.types.particle.types.NetworkParticleCrimsonSpore;
import protocolsupport.protocol.types.particle.types.NetworkParticleCrit;
import protocolsupport.protocol.types.particle.types.NetworkParticleCurrentDown;
import protocolsupport.protocol.types.particle.types.NetworkParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.NetworkParticleDolphin;
import protocolsupport.protocol.types.particle.types.NetworkParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingDripstoneLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingDripstoneWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingHoney;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingObsidianTear;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleDustTransition;
import protocolsupport.protocol.types.particle.types.NetworkParticleEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.NetworkParticleElectricSpark;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchant;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchantedHit;
import protocolsupport.protocol.types.particle.types.NetworkParticleEndRod;
import protocolsupport.protocol.types.particle.types.NetworkParticleEntityEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleExplosion;
import protocolsupport.protocol.types.particle.types.NetworkParticleExplosionEmitter;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingBlossom;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDripstoneLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDripstoneWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingHoney;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingNectar;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingObsidianTear;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleFirework;
import protocolsupport.protocol.types.particle.types.NetworkParticleFishing;
import protocolsupport.protocol.types.particle.types.NetworkParticleFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleFlash;
import protocolsupport.protocol.types.particle.types.NetworkParticleGlow;
import protocolsupport.protocol.types.particle.types.NetworkParticleGlowSquidInk;
import protocolsupport.protocol.types.particle.types.NetworkParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleHeart;
import protocolsupport.protocol.types.particle.types.NetworkParticleInstantEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleItem;
import protocolsupport.protocol.types.particle.types.NetworkParticleItemSlime;
import protocolsupport.protocol.types.particle.types.NetworkParticleItemSnowball;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingHoney;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingObsidianTear;
import protocolsupport.protocol.types.particle.types.NetworkParticleLargeSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleMycelium;
import protocolsupport.protocol.types.particle.types.NetworkParticleNautilus;
import protocolsupport.protocol.types.particle.types.NetworkParticleNote;
import protocolsupport.protocol.types.particle.types.NetworkParticlePoof;
import protocolsupport.protocol.types.particle.types.NetworkParticlePortal;
import protocolsupport.protocol.types.particle.types.NetworkParticleRain;
import protocolsupport.protocol.types.particle.types.NetworkParticleReversePortal;
import protocolsupport.protocol.types.particle.types.NetworkParticleScrape;
import protocolsupport.protocol.types.particle.types.NetworkParticleSmallFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleSneeze;
import protocolsupport.protocol.types.particle.types.NetworkParticleSnowflake;
import protocolsupport.protocol.types.particle.types.NetworkParticleSoul;
import protocolsupport.protocol.types.particle.types.NetworkParticleSoulFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleSpit;
import protocolsupport.protocol.types.particle.types.NetworkParticleSplash;
import protocolsupport.protocol.types.particle.types.NetworkParticleSporeBlossomAir;
import protocolsupport.protocol.types.particle.types.NetworkParticleSquidInk;
import protocolsupport.protocol.types.particle.types.NetworkParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.NetworkParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.NetworkParticleUnderwater;
import protocolsupport.protocol.types.particle.types.NetworkParticleVibration;
import protocolsupport.protocol.types.particle.types.NetworkParticleWarpedSpore;
import protocolsupport.protocol.types.particle.types.NetworkParticleWaxOff;
import protocolsupport.protocol.types.particle.types.NetworkParticleWaxOn;
import protocolsupport.protocol.types.particle.types.NetworkParticleWhiteAsh;
import protocolsupport.protocol.types.particle.types.NetworkParticleWitch;
import protocolsupport.protocol.utils.minecraftdata.MinecraftParticleData;
import protocolsupport.zplatform.ServerPlatform;

public class NetworkParticleRegistry {

	private NetworkParticleRegistry() {
	}

	private static final Int2ObjectMap<Supplier<NetworkParticle>> idToParticle = new Int2ObjectArrayMap<>(64);
	private static final Object2IntMap<Class<? extends NetworkParticle>> classToId = new Object2IntOpenHashMap<>();
	static {
		classToId.defaultReturnValue(-1);
	}

	private static void register(Supplier<NetworkParticle> particleSupplier, Particle bukkitParticle) {
		int pacticleId = MinecraftParticleData.getIdByName(ServerPlatform.get().getMiscUtils().getParticleKey(bukkitParticle).toString());
		idToParticle.put(pacticleId, particleSupplier);
		classToId.put(particleSupplier.get().getClass(), pacticleId);
	}

	static {
		register(NetworkParticleAmbientEntityEffect::new, Particle.SPELL_MOB_AMBIENT);
		register(NetworkParticleAngryVillager::new, Particle.VILLAGER_ANGRY);
		register(NetworkParticleBlockMarker::new, Particle.BLOCK_MARKER);
		register(NetworkParticleBlock::new, Particle.BLOCK_CRACK);
		register(NetworkParticleBubble::new, Particle.WATER_BUBBLE);
		register(NetworkParticleCloud::new, Particle.CLOUD);
		register(NetworkParticleCrit::new, Particle.CRIT);
		register(NetworkParticleDamageIndicator::new, Particle.DAMAGE_INDICATOR);
		register(NetworkParticleDragonBreath::new, Particle.DRAGON_BREATH);
		register(NetworkParticleDrippingLava::new, Particle.DRIP_LAVA);
		register(NetworkParticleFallingLava::new, Particle.FALLING_LAVA);
		register(NetworkParticleLandingLava::new, Particle.LANDING_LAVA);
		register(NetworkParticleDrippingWater::new, Particle.DRIP_WATER);
		register(NetworkParticleFallingWater::new, Particle.FALLING_WATER);
		register(NetworkParticleDust::new, Particle.REDSTONE);
		register(NetworkParticleDustTransition::new, Particle.DUST_COLOR_TRANSITION);
		register(NetworkParticleEffect::new, Particle.SPELL);
		register(NetworkParticleElderGuardian::new, Particle.MOB_APPEARANCE);
		register(NetworkParticleEnchantedHit::new, Particle.CRIT_MAGIC);
		register(NetworkParticleEnchant::new, Particle.ENCHANTMENT_TABLE);
		register(NetworkParticleEndRod::new, Particle.END_ROD);
		register(NetworkParticleEntityEffect::new, Particle.SPELL_MOB);
		register(NetworkParticleExplosionEmitter::new, Particle.EXPLOSION_HUGE);
		register(NetworkParticleExplosion::new, Particle.EXPLOSION_LARGE);
		register(NetworkParticleFallingDust::new, Particle.FALLING_DUST);
		register(NetworkParticleFirework::new, Particle.FIREWORKS_SPARK);
		register(NetworkParticleFishing::new, Particle.WATER_WAKE);
		register(NetworkParticleFlame::new, Particle.FLAME);
		register(NetworkParticleSoulFlame::new, Particle.SOUL_FIRE_FLAME);
		register(NetworkParticleSoul::new, Particle.SOUL);
		register(NetworkParticleFlash::new, Particle.FLASH);
		register(NetworkParticleHappyVillager::new, Particle.VILLAGER_HAPPY);
		register(NetworkParticleComposter::new, Particle.COMPOSTER);
		register(NetworkParticleHeart::new, Particle.HEART);
		register(NetworkParticleInstantEffect::new, Particle.SPELL_INSTANT);
		register(NetworkParticleItem::new, Particle.ITEM_CRACK);
		register(NetworkParticleVibration::new, Particle.VIBRATION);
		register(NetworkParticleItemSlime::new, Particle.SLIME);
		register(NetworkParticleItemSnowball::new, Particle.SNOWBALL);
		register(NetworkParticleLargeSmoke::new, Particle.SMOKE_LARGE);
		register(NetworkParticleLava::new, Particle.LAVA);
		register(NetworkParticleMycelium::new, Particle.TOWN_AURA);
		register(NetworkParticleNote::new, Particle.NOTE);
		register(NetworkParticlePoof::new, Particle.EXPLOSION_NORMAL);
		register(NetworkParticlePortal::new, Particle.PORTAL);
		register(NetworkParticleRain::new, Particle.WATER_DROP);
		register(NetworkParticleSmoke::new, Particle.SMOKE_NORMAL);
		register(NetworkParticleSneeze::new, Particle.SNEEZE);
		register(NetworkParticleSpit::new, Particle.SPIT);
		register(NetworkParticleSquidInk::new, Particle.SQUID_INK);
		register(NetworkParticleSweepAttack::new, Particle.SWEEP_ATTACK);
		register(NetworkParticleTotemOfUndying::new, Particle.TOTEM);
		register(NetworkParticleUnderwater::new, Particle.SUSPENDED);
		register(NetworkParticleSplash::new, Particle.WATER_SPLASH);
		register(NetworkParticleWitch::new, Particle.SPELL_WITCH);
		register(NetworkParticleBubblePop::new, Particle.BUBBLE_POP);
		register(NetworkParticleCurrentDown::new, Particle.CURRENT_DOWN);
		register(NetworkParticleBubbleColumnUp::new, Particle.BUBBLE_COLUMN_UP);
		register(NetworkParticleNautilus::new, Particle.NAUTILUS);
		register(NetworkParticleDolphin::new, Particle.DOLPHIN);
		register(NetworkParticleCampfireCozySmoke::new, Particle.CAMPFIRE_COSY_SMOKE);
		register(NetworkParticleCampfireSignalSmoke::new, Particle.CAMPFIRE_SIGNAL_SMOKE);
		register(NetworkParticleDrippingHoney::new, Particle.DRIPPING_HONEY);
		register(NetworkParticleFallingHoney::new, Particle.FALLING_HONEY);
		register(NetworkParticleLandingHoney::new, Particle.LANDING_HONEY);
		register(NetworkParticleFallingNectar::new, Particle.FALLING_NECTAR);
		register(NetworkParticleFallingBlossom::new, Particle.FALLING_SPORE_BLOSSOM);
		register(NetworkParticleAsh::new, Particle.ASH);
		register(NetworkParticleCrimsonSpore::new, Particle.CRIMSON_SPORE);
		register(NetworkParticleWarpedSpore::new, Particle.WARPED_SPORE);
		register(NetworkParticleSporeBlossomAir::new, Particle.SPORE_BLOSSOM_AIR);
		register(NetworkParticleDrippingObsidianTear::new, Particle.DRIPPING_OBSIDIAN_TEAR);
		register(NetworkParticleFallingObsidianTear::new, Particle.FALLING_OBSIDIAN_TEAR);
		register(NetworkParticleLandingObsidianTear::new, Particle.LANDING_OBSIDIAN_TEAR);
		register(NetworkParticleReversePortal::new, Particle.REVERSE_PORTAL);
		register(NetworkParticleWhiteAsh::new, Particle.WHITE_ASH);
		register(NetworkParticleSnowflake::new, Particle.SNOWFLAKE);
		register(NetworkParticleSmallFlame::new, Particle.SMALL_FLAME);
		register(NetworkParticleDrippingDripstoneLava::new, Particle.DRIPPING_DRIPSTONE_LAVA);
		register(NetworkParticleFallingDripstoneLava::new, Particle.FALLING_DRIPSTONE_LAVA);
		register(NetworkParticleDrippingDripstoneWater::new, Particle.DRIPPING_DRIPSTONE_WATER);
		register(NetworkParticleFallingDripstoneWater::new, Particle.FALLING_DRIPSTONE_WATER);
		register(NetworkParticleGlowSquidInk::new, Particle.GLOW_SQUID_INK);
		register(NetworkParticleGlow::new, Particle.GLOW);
		register(NetworkParticleWaxOn::new, Particle.WAX_ON);
		register(NetworkParticleWaxOff::new, Particle.WAX_OFF);
		register(NetworkParticleElectricSpark::new, Particle.ELECTRIC_SPARK);
		register(NetworkParticleScrape::new, Particle.SCRAPE);
	}

	public static List<NetworkParticle> getAll() {
		return
			idToParticle.values().stream()
			.map(Supplier::get)
			.toList();
	}

	public static @Nonnull NetworkParticle fromId(@Nonnegative int id) {
		Supplier<NetworkParticle> particleSupplier = idToParticle.get(id);
		if (particleSupplier == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown particle network id {0}", id));
		}
		return particleSupplier.get();
	}

	public static @CheckForSigned int getId(@Nonnull NetworkParticle particle) {
		return classToId.getInt(particle.getClass());
	}

}
