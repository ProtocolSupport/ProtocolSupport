package protocolsupport.protocol.utils.types.particle;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class Particle {

	public static int SKIP = -1;
	private static Int2ObjectMap<Supplier<Particle>> idToParticle = new Int2ObjectArrayMap<>(50);
	private static Object2IntMap<Class <? extends Particle>> particleToId = new Object2IntArrayMap<>(50);
	private static int pId = 0;
	private static void registerParticle(Supplier<Particle> particle) {
		idToParticle.put(pId, particle);
		pId++;
	}
	static {
		registerParticle(() -> new ParticleAngryVillager(pId));
		registerParticle(() -> new ParticleBarrier(pId));
		registerParticle(() -> new ParticleBlock(pId));
		registerParticle(() -> new ParticleBubble(pId));
		registerParticle(() -> new ParticleCloud(pId));
		registerParticle(() -> new ParticleCrit(pId));
		registerParticle(() -> new ParticleDamageIndicator(pId));
		registerParticle(() -> new ParticleDragonBreath(pId));
		registerParticle(() -> new ParticleDrippingLava(pId));
		registerParticle(() -> new ParticleDrippingWater(pId));
		registerParticle(() -> new ParticleDust(pId));
		registerParticle(() -> new ParticleEffect(pId));
		registerParticle(() -> new ParticleElderGuardian(pId));
		registerParticle(() -> new ParticleEnchantedHit(pId));
		registerParticle(() -> new ParticleEnchant(pId));
		registerParticle(() -> new ParticleEndRod(pId));
		registerParticle(() -> new ParticleEntityEffect(pId));
		registerParticle(() -> new ParticleExplosionEmitter(pId));
		registerParticle(() -> new ParticleExplosion(pId));
		registerParticle(() -> new ParticleFallingDust(pId));
		registerParticle(() -> new ParticleFirework(pId));
		registerParticle(() -> new ParticleFishing(pId));
		registerParticle(() -> new ParticleFlame(pId));
		registerParticle(() -> new ParticleHappyVillager(pId));
		registerParticle(() -> new ParticleHeart(pId));
		registerParticle(() -> new ParticleInstantEffect(pId));
		registerParticle(() -> new ParticleItem(pId));
		registerParticle(() -> new ParticleItemSlime(pId));
		registerParticle(() -> new ParticleItemSnowball(pId));
		registerParticle(() -> new ParticleLargeSmoke(pId));
		registerParticle(() -> new ParticleLava(pId));
		registerParticle(() -> new ParticleMycelium(pId));
		registerParticle(() -> new ParticleNote(pId));
		registerParticle(() -> new ParticlePoof(pId));
		registerParticle(() -> new ParticlePortal(pId));
		registerParticle(() -> new ParticleRain(pId));
		registerParticle(() -> new ParticleSmoke(pId));
		registerParticle(() -> new ParticleSpit(pId));
		registerParticle(() -> new ParticleSquidInk(pId));
		registerParticle(() -> new ParticleSweepAttack(pId));
		registerParticle(() -> new ParticleTotemOfUndying(pId));
		registerParticle(() -> new ParticleUnderwater(pId));
		registerParticle(() -> new ParticleSplash(pId));
		registerParticle(() -> new ParticleWitch(pId));
		registerParticle(() -> new ParticleBubblePop(pId));
		registerParticle(() -> new ParticleCurrentDown(pId));
		registerParticle(() -> new ParticleBubbleColumnUp(pId));
		registerParticle(() -> new ParticleNautilus(pId));
		registerParticle(() -> new ParticleDolphin(pId));
		registerParticle(() -> new ParticleSkip());
	}

	public static Particle fromId(int id) {
		return idToParticle.getOrDefault(id, () -> null).get();
	}

	public static int toId(Class<? extends Particle> particle) {
		return particleToId.getOrDefault(particle, -1);
	}

	public Particle(int id, String name) {
		this.id = id;
		this.name = name;
		particleToId.put(this.getClass(), id);
	}

	private final String name;
	private final int id;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void readData(ByteBuf buf) { };

	public void writeData(ByteBuf buf) { };

}
