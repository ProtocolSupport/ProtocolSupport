package protocolsupport.protocol.types.particle;

import java.util.function.IntFunction;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class ParticleRegistry {

	private static final Int2ObjectMap<IntFunction<Particle>> idToParticle = new Int2ObjectArrayMap<>(50);
	private static int nextParticleId = 0;

	private static void register(IntFunction<Particle> particle) {
		idToParticle.put(nextParticleId, particle);
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
	}

	public static Particle fromId(int id) {
		return idToParticle.getOrDefault(id, pId -> null).apply(id);
	}

}
