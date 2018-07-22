package protocolsupport.protocol.typeremapper.particle;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.protocol.utils.types.particle.ParticleAmbientEntityEffect;
import protocolsupport.protocol.utils.types.particle.ParticleAngryVillager;
import protocolsupport.protocol.utils.types.particle.ParticleBarrier;
import protocolsupport.protocol.utils.types.particle.ParticleBlock;
import protocolsupport.protocol.utils.types.particle.ParticleBubble;
import protocolsupport.protocol.utils.types.particle.ParticleCloud;
import protocolsupport.protocol.utils.types.particle.ParticleCrit;
import protocolsupport.protocol.utils.types.particle.ParticleDamageIndicator;
import protocolsupport.protocol.utils.types.particle.ParticleDragonBreath;
import protocolsupport.protocol.utils.types.particle.ParticleDrippingLava;
import protocolsupport.protocol.utils.types.particle.ParticleDrippingWater;
import protocolsupport.protocol.utils.types.particle.ParticleDust;
import protocolsupport.protocol.utils.types.particle.ParticleEffect;
import protocolsupport.protocol.utils.types.particle.ParticleElderGuardian;
import protocolsupport.protocol.utils.types.particle.ParticleEnchant;
import protocolsupport.protocol.utils.types.particle.ParticleEnchantedHit;
import protocolsupport.protocol.utils.types.particle.ParticleEndRod;
import protocolsupport.protocol.utils.types.particle.ParticleEntityEffect;
import protocolsupport.protocol.utils.types.particle.ParticleExplosion;
import protocolsupport.protocol.utils.types.particle.ParticleExplosionEmitter;
import protocolsupport.protocol.utils.types.particle.ParticleFallingDust;
import protocolsupport.protocol.utils.types.particle.ParticleFirework;
import protocolsupport.protocol.utils.types.particle.ParticleFishing;
import protocolsupport.protocol.utils.types.particle.ParticleFlame;
import protocolsupport.protocol.utils.types.particle.ParticleHappyVillager;
import protocolsupport.protocol.utils.types.particle.ParticleHeart;
import protocolsupport.protocol.utils.types.particle.ParticleInstantEffect;
import protocolsupport.protocol.utils.types.particle.ParticleItem;
import protocolsupport.protocol.utils.types.particle.ParticleItemSlime;
import protocolsupport.protocol.utils.types.particle.ParticleItemSnowball;
import protocolsupport.protocol.utils.types.particle.ParticleLargeSmoke;
import protocolsupport.protocol.utils.types.particle.ParticleLava;
import protocolsupport.protocol.utils.types.particle.ParticleMycelium;
import protocolsupport.protocol.utils.types.particle.ParticleNote;
import protocolsupport.protocol.utils.types.particle.ParticlePoof;
import protocolsupport.protocol.utils.types.particle.ParticlePortal;
import protocolsupport.protocol.utils.types.particle.ParticleRain;
import protocolsupport.protocol.utils.types.particle.ParticleSkip;
import protocolsupport.protocol.utils.types.particle.ParticleSmoke;
import protocolsupport.protocol.utils.types.particle.ParticleSpit;
import protocolsupport.protocol.utils.types.particle.ParticleSplash;
import protocolsupport.protocol.utils.types.particle.ParticleSweepAttack;
import protocolsupport.protocol.utils.types.particle.ParticleTotemOfUndying;
import protocolsupport.protocol.utils.types.particle.ParticleUnderwater;
import protocolsupport.protocol.utils.types.particle.ParticleWitch;

public class ParticleRemapper {

	
	private static final RemappingRegistry<ParticleRemappingTable> REMAPS = new RemappingRegistry<ParticleRemappingTable>(){
		{
			//Legacy (<1.13) remaps for old ids and names.
			registerRemap(ParticlePoof.class, () -> new Particle(0, "explode"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleExplosion.class, () -> new Particle(1, "largeexplode"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleExplosionEmitter.class, () -> new Particle(2, "hugeexplosion"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFirework.class, () -> new Particle(3, "fireworksSpark"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleBubble.class, () -> new Particle(4, "bubble"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleSplash.class, () -> new Particle(5, "splash"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFishing.class, () -> new Particle(6, "wake"), ProtocolVersionsHelper.RANGE__1_7_5__1_12_2);
			registerRemap(ParticleUnderwater.class, () -> new Particle(7, "suspended"), ProtocolVersionsHelper.BEFORE_1_13);
			//registerRemap(ParticleDepth.class, () -> new Particle(8, "depthsuspend"), ProtocolVersionsHelper.BEFORE_1_13); TODO: I think this is removed.
			registerRemap(ParticleCrit.class, () -> new Particle(9, "crit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEnchantedHit.class, () -> new Particle(10, "magicCrit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleSmoke.class, () -> new Particle(11, "smoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleLargeSmoke.class, () -> new Particle(12, "largesmoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEffect.class, () -> new Particle(13, "spell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleInstantEffect.class, () -> new Particle(14, "instantSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEntityEffect.class, () -> new Particle(15, "mobSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleAmbientEntityEffect.class, () -> new Particle(16, "mobSpellAmbient"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleWitch.class, () -> new Particle(17, "witchMagic"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDrippingWater.class, () -> new Particle(18, "dripWater"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDrippingLava.class, () -> new Particle(19, "dripLava"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleAngryVillager.class, () -> new Particle(20, "angryVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleHappyVillager.class, () -> new Particle(21, "happyVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleMycelium.class, () -> new Particle(22, "townaura"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleNote.class, () -> new Particle(23, "note"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticlePortal.class, () -> new Particle(24, "portal"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEnchant.class, () -> new Particle(25, "enchantmenttable"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFlame.class, () -> new Particle(26, "flame"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleLava.class, () -> new Particle(27, "lava"), ProtocolVersionsHelper.BEFORE_1_13);
			//registerRemap(ParticleFootStep.class, () -> new Particle(28, "footstep"), ProtocolVersionsHelper.BEFORE_1_13); TODO: I think removed :/
			registerRemap(ParticleCloud.class, () -> new Particle(29, "cloud"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDust.class, () -> new Particle(30, "reddust"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleItemSnowball.class, () -> new Particle(31, "snowballpoof"), ProtocolVersionsHelper.BEFORE_1_13);
			//registerRemap(ParticlePoof.class, () -> new Particle(32, "snowshovel"), ProtocolVersionsHelper.BEFORE_1_13); TODO: removed?
			registerRemap(ParticleItemSlime.class, () -> new Particle(33, "slime"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleHeart.class, () -> new Particle(34, "heart"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleBarrier.class, () -> new Particle(35, "barrier"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerRemap(ParticleItem.class, () -> new Particle(36, "iconcrack"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleBlock.class, () -> new Particle(37, "blockcrack"), ProtocolVersionsHelper.BEFORE_1_13);
			//registerRemap(ParticleBlock.class, () -> new Particle(38, "blockdust"), ProtocolVersionsHelper.BEFORE_1_13); TODO: Gone?
			registerRemap(ParticleRain.class, () -> new Particle(39, "droplet"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			//registerRemap(ParticleTake.class, () -> new Particle(40, "take"), ProtocolVersionsHelper.BEFORE_1_13); TODO: GONE
			registerRemap(ParticleElderGuardian.class, () -> new Particle(41, "mobappearance"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerRemap(ParticleDragonBreath.class, () -> new Particle(42, "dragonbreath"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleEndRod.class, () -> new Particle(43, "endRod"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleDamageIndicator.class, () -> new Particle(44, "damageIndicator"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleSweepAttack.class, () -> new Particle(45, "sweepAttack"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleFallingDust.class, () -> new Particle(46, "fallingdust"), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
			registerRemap(ParticleTotemOfUndying.class, () -> new Particle(47, "totem"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);
			registerRemap(ParticleSpit.class, () -> new Particle(48, "spit"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);

			//Skip some particles for older versions.
			registerSkip(ParticleFishing.class, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkip(ParticleBarrier.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleRain.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleElderGuardian.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleDragonBreath.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleEndRod.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleDamageIndicator.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleSweepAttack.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleFallingDust.class, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkip(ParticleTotemOfUndying.class, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkip(ParticleSpit.class, ProtocolVersionsHelper.BEFORE_1_11);
		}
		protected void registerSkip(Class<? extends Particle> from, ProtocolVersion... versions) {
			registerRemap(from, ParticleSkip.class, versions);
		}
		protected void registerRemap(Class<? extends Particle> from, Class<? extends Particle> to, ProtocolVersion... versions) {
			registerRemap(from, () -> Particle.fromId(Particle.toId(to)), versions);
		}
		protected void registerRemap(Class<? extends Particle> from, Supplier<Particle> to, ProtocolVersion... versions) {
			registerRemap(from, (part) -> to.get(), versions);
		}
		protected void registerRemap(Class<? extends Particle> from, Function<Particle, Particle> remapFunction, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				REMAPS.getTable(version).setRemap(from, remapFunction);
			}
		}
		@Override
		protected ParticleRemappingTable createTable() {
			return new ParticleRemappingTable();
		}
	};

	private static class ParticleRemappingTable extends RemappingTable {

		protected final HashMap<Class<? extends Particle>, Function<Particle, Particle>> table = new HashMap<>();

		public void setRemap(Class<? extends Particle> particle, Function<Particle, Particle> remap) {
			table.put(particle, remap);
		}

		public Function<Particle, Particle> getRemap(Class<? extends Particle> particle) {
			return table.getOrDefault(particle, (part) -> part);
		}

	}

	public static Particle remap(ProtocolVersion version, Particle particle) {
		return REMAPS.getTable(version).getRemap(particle.getClass()).apply(particle);
	}

}
