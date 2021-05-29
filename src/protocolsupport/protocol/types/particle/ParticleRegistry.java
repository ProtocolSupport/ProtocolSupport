package protocolsupport.protocol.types.particle;

import java.text.MessageFormat;
import java.util.function.Supplier;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.types.particle.types.ParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.types.ParticleAngryVillager;
import protocolsupport.protocol.types.particle.types.ParticleAsh;
import protocolsupport.protocol.types.particle.types.ParticleBarrier;
import protocolsupport.protocol.types.particle.types.ParticleBlock;
import protocolsupport.protocol.types.particle.types.ParticleBubble;
import protocolsupport.protocol.types.particle.types.ParticleBubbleColumnUp;
import protocolsupport.protocol.types.particle.types.ParticleBubblePop;
import protocolsupport.protocol.types.particle.types.ParticleCampfireCozySmoke;
import protocolsupport.protocol.types.particle.types.ParticleCampfireSignalSmoke;
import protocolsupport.protocol.types.particle.types.ParticleCloud;
import protocolsupport.protocol.types.particle.types.ParticleComposter;
import protocolsupport.protocol.types.particle.types.ParticleCrimsonSpore;
import protocolsupport.protocol.types.particle.types.ParticleCrit;
import protocolsupport.protocol.types.particle.types.ParticleCurrentDown;
import protocolsupport.protocol.types.particle.types.ParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.ParticleDolphin;
import protocolsupport.protocol.types.particle.types.ParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.ParticleDrippingHoney;
import protocolsupport.protocol.types.particle.types.ParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.ParticleDrippingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.ParticleDust;
import protocolsupport.protocol.types.particle.types.ParticleEffect;
import protocolsupport.protocol.types.particle.types.ParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.ParticleEnchant;
import protocolsupport.protocol.types.particle.types.ParticleEnchantedHit;
import protocolsupport.protocol.types.particle.types.ParticleEndRod;
import protocolsupport.protocol.types.particle.types.ParticleEntityEffect;
import protocolsupport.protocol.types.particle.types.ParticleExplosion;
import protocolsupport.protocol.types.particle.types.ParticleExplosionEmitter;
import protocolsupport.protocol.types.particle.types.ParticleFallingDust;
import protocolsupport.protocol.types.particle.types.ParticleFallingHoney;
import protocolsupport.protocol.types.particle.types.ParticleFallingLava;
import protocolsupport.protocol.types.particle.types.ParticleFallingNectar;
import protocolsupport.protocol.types.particle.types.ParticleFallingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleFallingWater;
import protocolsupport.protocol.types.particle.types.ParticleFirework;
import protocolsupport.protocol.types.particle.types.ParticleFishing;
import protocolsupport.protocol.types.particle.types.ParticleFlame;
import protocolsupport.protocol.types.particle.types.ParticleFlash;
import protocolsupport.protocol.types.particle.types.ParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.ParticleHeart;
import protocolsupport.protocol.types.particle.types.ParticleInstantEffect;
import protocolsupport.protocol.types.particle.types.ParticleItem;
import protocolsupport.protocol.types.particle.types.ParticleItemSlime;
import protocolsupport.protocol.types.particle.types.ParticleItemSnowball;
import protocolsupport.protocol.types.particle.types.ParticleLandingHoney;
import protocolsupport.protocol.types.particle.types.ParticleLandingLava;
import protocolsupport.protocol.types.particle.types.ParticleLandingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleLargeSmoke;
import protocolsupport.protocol.types.particle.types.ParticleLava;
import protocolsupport.protocol.types.particle.types.ParticleMycelium;
import protocolsupport.protocol.types.particle.types.ParticleNautilus;
import protocolsupport.protocol.types.particle.types.ParticleNote;
import protocolsupport.protocol.types.particle.types.ParticlePoof;
import protocolsupport.protocol.types.particle.types.ParticlePortal;
import protocolsupport.protocol.types.particle.types.ParticleRain;
import protocolsupport.protocol.types.particle.types.ParticleReversePortal;
import protocolsupport.protocol.types.particle.types.ParticleSmoke;
import protocolsupport.protocol.types.particle.types.ParticleSneeze;
import protocolsupport.protocol.types.particle.types.ParticleSoul;
import protocolsupport.protocol.types.particle.types.ParticleSoulFlame;
import protocolsupport.protocol.types.particle.types.ParticleSpit;
import protocolsupport.protocol.types.particle.types.ParticleSplash;
import protocolsupport.protocol.types.particle.types.ParticleSquidInk;
import protocolsupport.protocol.types.particle.types.ParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.ParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.ParticleUnderwater;
import protocolsupport.protocol.types.particle.types.ParticleWarpedSpore;
import protocolsupport.protocol.types.particle.types.ParticleWhiteAsh;
import protocolsupport.protocol.types.particle.types.ParticleWitch;

public class ParticleRegistry {

	private ParticleRegistry() {
	}

	private static final Int2ObjectMap<Supplier<Particle>> idToParticle = new Int2ObjectArrayMap<>(64);
	private static final Object2IntMap<Class<? extends Particle>> classToId = new Object2IntOpenHashMap<>();
	static {
		classToId.defaultReturnValue(-1);
	}
	private static int nextParticleId = 0;

	private static void register(Supplier<Particle> particle) {
		idToParticle.put(nextParticleId, particle);
		classToId.put(particle.get().getClass(), nextParticleId);
		nextParticleId++;
	}

	static {
		register(ParticleAmbientEntityEffect::new);
		register(ParticleAngryVillager::new);
		register(ParticleBarrier::new);
		register(ParticleBlock::new);
		register(ParticleBubble::new);
		register(ParticleCloud::new);
		register(ParticleCrit::new);
		register(ParticleDamageIndicator::new);
		register(ParticleDragonBreath::new);
		register(ParticleDrippingLava::new);
		register(ParticleFallingLava::new);
		register(ParticleLandingLava::new);
		register(ParticleDrippingWater::new);
		register(ParticleFallingWater::new);
		register(ParticleDust::new);
		register(ParticleEffect::new);
		register(ParticleElderGuardian::new);
		register(ParticleEnchantedHit::new);
		register(ParticleEnchant::new);
		register(ParticleEndRod::new);
		register(ParticleEntityEffect::new);
		register(ParticleExplosionEmitter::new);
		register(ParticleExplosion::new);
		register(ParticleFallingDust::new);
		register(ParticleFirework::new);
		register(ParticleFishing::new);
		register(ParticleFlame::new);
		register(ParticleSoulFlame::new);
		register(ParticleSoul::new);
		register(ParticleFlash::new);
		register(ParticleHappyVillager::new);
		register(ParticleComposter::new);
		register(ParticleHeart::new);
		register(ParticleInstantEffect::new);
		register(ParticleItem::new);
		register(ParticleItemSlime::new);
		register(ParticleItemSnowball::new);
		register(ParticleLargeSmoke::new);
		register(ParticleLava::new);
		register(ParticleMycelium::new);
		register(ParticleNote::new);
		register(ParticlePoof::new);
		register(ParticlePortal::new);
		register(ParticleRain::new);
		register(ParticleSmoke::new);
		register(ParticleSneeze::new);
		register(ParticleSpit::new);
		register(ParticleSquidInk::new);
		register(ParticleSweepAttack::new);
		register(ParticleTotemOfUndying::new);
		register(ParticleUnderwater::new);
		register(ParticleSplash::new);
		register(ParticleWitch::new);
		register(ParticleBubblePop::new);
		register(ParticleCurrentDown::new);
		register(ParticleBubbleColumnUp::new);
		register(ParticleNautilus::new);
		register(ParticleDolphin::new);
		register(ParticleCampfireCozySmoke::new);
		register(ParticleCampfireSignalSmoke::new);
		register(ParticleDrippingHoney::new);
		register(ParticleFallingHoney::new);
		register(ParticleLandingHoney::new);
		register(ParticleFallingNectar::new);
		register(ParticleAsh::new);
		register(ParticleCrimsonSpore::new);
		register(ParticleWarpedSpore::new);
		register(ParticleDrippingObsidianTear::new);
		register(ParticleFallingObsidianTear::new);
		register(ParticleLandingObsidianTear::new);
		register(ParticleReversePortal::new);
		register(ParticleWhiteAsh::new);
	}

	public static @Nonnull Particle fromId(@Nonnegative int id) {
		Supplier<Particle> particleSupplier = idToParticle.get(id);
		if (particleSupplier == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown particle network id {0}", id));
		}
		return particleSupplier.get();
	}

	public static @CheckForSigned int getId(@Nonnull Particle particle) {
		return classToId.getInt(particle.getClass());
	}

}
