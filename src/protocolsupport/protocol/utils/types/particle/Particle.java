package protocolsupport.protocol.utils.types.particle;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import protocolsupport.api.ProtocolVersion;

public class Particle {

	public static int SKIP = -1;
	private static Int2ObjectMap<Function<Integer, Particle>> idToParticle = new Int2ObjectArrayMap<>(50);
	private static Object2IntMap<Class <? extends Particle>> particleToId = new Object2IntArrayMap<>(50);
	private static int pId = 0;
	private static void registerParticle(Function<Integer, Particle> particle) {
		idToParticle.put(pId, particle);
		particleToId.put(particle.apply(pId).getClass(), pId);
		pId++;
	}
	static {
		registerParticle((pId) -> new ParticleAmbientEntityEffect(pId));
		registerParticle((pId) -> new ParticleAngryVillager(pId));
		registerParticle((pId) -> new ParticleBarrier(pId));
		registerParticle((pId) -> new ParticleBlock(pId));
		registerParticle((pId) -> new ParticleBubble(pId));
		registerParticle((pId) -> new ParticleCloud(pId));
		registerParticle((pId) -> new ParticleCrit(pId));
		registerParticle((pId) -> new ParticleDamageIndicator(pId));
		registerParticle((pId) -> new ParticleDragonBreath(pId));
		registerParticle((pId) -> new ParticleDrippingLava(pId));
		registerParticle((pId) -> new ParticleDrippingWater(pId));
		registerParticle((pId) -> new ParticleDust(pId));
		registerParticle((pId) -> new ParticleEffect(pId));
		registerParticle((pId) -> new ParticleElderGuardian(pId));
		registerParticle((pId) -> new ParticleEnchantedHit(pId));
		registerParticle((pId) -> new ParticleEnchant(pId));
		registerParticle((pId) -> new ParticleEndRod(pId));
		registerParticle((pId) -> new ParticleEntityEffect(pId));
		registerParticle((pId) -> new ParticleExplosionEmitter(pId));
		registerParticle((pId) -> new ParticleExplosion(pId));
		registerParticle((pId) -> new ParticleFallingDust(pId));
		registerParticle((pId) -> new ParticleFirework(pId));
		registerParticle((pId) -> new ParticleFishing(pId));
		registerParticle((pId) -> new ParticleFlame(pId));
		registerParticle((pId) -> new ParticleHappyVillager(pId));
		registerParticle((pId) -> new ParticleHeart(pId));
		registerParticle((pId) -> new ParticleInstantEffect(pId));
		registerParticle((pId) -> new ParticleItem(pId));
		registerParticle((pId) -> new ParticleItemSlime(pId));
		registerParticle((pId) -> new ParticleItemSnowball(pId));
		registerParticle((pId) -> new ParticleLargeSmoke(pId));
		registerParticle((pId) -> new ParticleLava(pId));
		registerParticle((pId) -> new ParticleMycelium(pId));
		registerParticle((pId) -> new ParticleNote(pId));
		registerParticle((pId) -> new ParticlePoof(pId));
		registerParticle((pId) -> new ParticlePortal(pId));
		registerParticle((pId) -> new ParticleRain(pId));
		registerParticle((pId) -> new ParticleSmoke(pId));
		registerParticle((pId) -> new ParticleSpit(pId));
		registerParticle((pId) -> new ParticleSquidInk(pId));
		registerParticle((pId) -> new ParticleSweepAttack(pId));
		registerParticle((pId) -> new ParticleTotemOfUndying(pId));
		registerParticle((pId) -> new ParticleUnderwater(pId));
		registerParticle((pId) -> new ParticleSplash(pId));
		registerParticle((pId) -> new ParticleWitch(pId));
		registerParticle((pId) -> new ParticleBubblePop(pId));
		registerParticle((pId) -> new ParticleCurrentDown(pId));
		registerParticle((pId) -> new ParticleBubbleColumnUp(pId));
		registerParticle((pId) -> new ParticleNautilus(pId));
		registerParticle((pId) -> new ParticleDolphin(pId));
		registerParticle((pId) -> new ParticleSkip());
	}

	public static Particle fromId(int id) {
		return idToParticle.getOrDefault(id, (pId) -> null).apply(id);
	}

	public static int toId(Class<? extends Particle> particle) {
		return particleToId.getOrDefault(particle, -1);
	}

	public Particle(int id, String name) {
		this.id = id;
		this.name = name;
	}

	protected String name;
	protected int id;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void readData(ByteBuf buf) { };

	public void remap(ProtocolVersion version, String locale) { };

	public void writeData(ByteBuf buf) { };

}
