package protocolsupport.protocol.typeremapper.particle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleBlockCrack;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleFallingDust;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleIconCrack;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
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
import protocolsupportbuildprocessor.Preload;

@Preload
public class ParticleRemapper {

	protected static final RemappingRegistry<ParticleRemappingTable> REGISTRY = new RemappingRegistry<ParticleRemappingTable>() {
		{
			Arrays.stream(ProtocolVersionsHelper.UP_1_13)
			.forEach(version -> {
				registerRemap(ParticleBlock.class, from -> new ParticleBlock(from.getId(), version, from.getBlockData()), version);
				registerRemap(ParticleItem.class, from -> new ParticleItem(from.getId(), version, I18NData.DEFAULT_LOCALE, from.getItemStack()), version);
			});

			LegacyParticle explode = new LegacyParticle(0, "explode");
			registerRemap(ParticlePoof.class, () -> explode, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDragonBreath.class, () -> explode, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemap(ParticleExplosion.class, () -> new LegacyParticle(1, "largeexplode"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleExplosionEmitter.class, () -> new LegacyParticle(2, "hugeexplosion"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFirework.class, () -> new LegacyParticle(3, "fireworksSpark"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleBubble.class, () -> new LegacyParticle(4, "bubble"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleSplash.class, () -> new LegacyParticle(5, "splash"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFishing.class, () -> new LegacyParticle(6, "wake"), ProtocolVersionsHelper.RANGE__1_7_5__1_12_2);
			registerRemap(ParticleUnderwater.class, () -> new LegacyParticle(7, "suspended"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleCrit.class, () -> new LegacyParticle(9, "crit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEnchantedHit.class, () -> new LegacyParticle(10, "magicCrit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleSmoke.class, () -> new LegacyParticle(11, "smoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleLargeSmoke.class, () -> new LegacyParticle(12, "largesmoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEffect.class, () -> new LegacyParticle(13, "spell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleInstantEffect.class, () -> new LegacyParticle(14, "instantSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEntityEffect.class, () -> new LegacyParticle(15, "mobSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleAmbientEntityEffect.class, () -> new LegacyParticle(16, "mobSpellAmbient"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleWitch.class, () -> new LegacyParticle(17, "witchMagic"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDrippingWater.class, () -> new LegacyParticle(18, "dripWater"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDrippingLava.class, () -> new LegacyParticle(19, "dripLava"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleAngryVillager.class, () -> new LegacyParticle(20, "angryVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleHappyVillager.class, () -> new LegacyParticle(21, "happyVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleMycelium.class, () -> new LegacyParticle(22, "townaura"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleNote.class, () -> new LegacyParticle(23, "note"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticlePortal.class, () -> new LegacyParticle(24, "portal"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleEnchant.class, () -> new LegacyParticle(25, "enchantmenttable"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleFlame.class, () -> new LegacyParticle(26, "flame"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleLava.class, () -> new LegacyParticle(27, "lava"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleCloud.class, () -> new LegacyParticle(29, "cloud"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleDust.class, () -> new LegacyParticle(30, "reddust"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleItemSnowball.class, () -> new LegacyParticle(31, "snowballpoof"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleItemSlime.class, () -> new LegacyParticle(33, "slime"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleHeart.class, () -> new LegacyParticle(34, "heart"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(ParticleBarrier.class, () -> new LegacyParticle(35, "barrier"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_13)
			.forEach(version -> {
				registerRemap(
					ParticleItem.class,
					from -> new LegacyParticleIconCrack(36, "iconcrack", version, I18NData.DEFAULT_LOCALE, from.getItemStack()),
					version
				);
				registerRemap(
					ParticleBlock.class,
					from -> new LegacyParticleBlockCrack(37, "blockcrack", version, from.getBlockData()),
					version
				);
			});
			registerRemap(ParticleRain.class, () -> new LegacyParticle(39, "droplet"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerRemap(ParticleElderGuardian.class, () -> new LegacyParticle(41, "mobappearance"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerRemap(ParticleDragonBreath.class, () -> new LegacyParticle(42, "dragonbreath"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleEndRod.class, () -> new LegacyParticle(43, "endRod"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleDamageIndicator.class, () -> new LegacyParticle(44, "damageIndicator"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleSweepAttack.class, () -> new LegacyParticle(45, "sweepAttack"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerRemap(ParticleFallingDust.class, from -> new LegacyParticleFallingDust(46, "fallingdust", from.getBlockstate()), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_10)
			.forEach(version ->
				registerRemap(
					ParticleFallingDust.class,
					from -> new LegacyParticleBlockCrack(37, "blockcrack", version, from.getBlockstate()),
					version
				)
			);
			registerRemap(ParticleTotemOfUndying.class, () -> new LegacyParticle(47, "totem"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);
			registerRemap(ParticleSpit.class, () -> new LegacyParticle(48, "spit"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);

			registerSkip(ParticleTotemOfUndying.class, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkip(ParticleSpit.class, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkip(ParticleDragonBreath.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleEndRod.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleDamageIndicator.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleSweepAttack.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleRain.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleElderGuardian.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleFishing.class, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkip(ParticleBarrier.class, ProtocolVersionsHelper.BEFORE_1_8);
		}
		protected void registerSkip(Class<? extends Particle> from, ProtocolVersion... versions) {
			registerRemap(from, ParticleSkip::new, versions);
		}
		protected void registerRemap(Class<? extends Particle> from, Supplier<Particle> to, ProtocolVersion... versions) {
			registerRemap(from, part -> to.get(), versions);
		}
		@SuppressWarnings("unchecked")
		protected <T extends Particle> void registerRemap(Class<T> from, Function<T, Particle> remapFunction, ProtocolVersion... versions) {
			Arrays.stream(versions)
			.forEach(version -> getTable(version).setRemap(from, (Function<Particle, Particle>) remapFunction));
		}
		@Override
		protected ParticleRemappingTable createTable() {
			return new ParticleRemappingTable();
		}
	};

	protected static class ParticleRemappingTable extends RemappingTable {

		protected final Map<Class<? extends Particle>, Function<Particle, Particle>> table = new HashMap<>();

		public void setRemap(Class<? extends Particle> particle, Function<Particle, Particle> remap) {
			table.put(particle, remap);
		}

		public Function<Particle, Particle> getRemap(Class<? extends Particle> particle) {
			return table.getOrDefault(particle, part -> part);
		}

	}

	public static Particle remap(ProtocolVersion version, Particle particle) {
		return REGISTRY.getTable(version).getRemap(particle.getClass()).apply(particle);
	}

}
