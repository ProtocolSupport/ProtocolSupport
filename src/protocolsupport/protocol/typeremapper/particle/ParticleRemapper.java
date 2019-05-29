package protocolsupport.protocol.typeremapper.particle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleBlockCrack;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleFallingDust;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticleIconCrack;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.ParticleAngryVillager;
import protocolsupport.protocol.types.particle.ParticleBarrier;
import protocolsupport.protocol.types.particle.ParticleBlock;
import protocolsupport.protocol.types.particle.ParticleBubble;
import protocolsupport.protocol.types.particle.ParticleCampfireCozySmoke;
import protocolsupport.protocol.types.particle.ParticleCampfireSignalSmoke;
import protocolsupport.protocol.types.particle.ParticleCloud;
import protocolsupport.protocol.types.particle.ParticleComposter;
import protocolsupport.protocol.types.particle.ParticleCrit;
import protocolsupport.protocol.types.particle.ParticleDamageIndicator;
import protocolsupport.protocol.types.particle.ParticleDragonBreath;
import protocolsupport.protocol.types.particle.ParticleDrippingLava;
import protocolsupport.protocol.types.particle.ParticleDrippingWater;
import protocolsupport.protocol.types.particle.ParticleDust;
import protocolsupport.protocol.types.particle.ParticleEffect;
import protocolsupport.protocol.types.particle.ParticleElderGuardian;
import protocolsupport.protocol.types.particle.ParticleEnchant;
import protocolsupport.protocol.types.particle.ParticleEnchantedHit;
import protocolsupport.protocol.types.particle.ParticleEndRod;
import protocolsupport.protocol.types.particle.ParticleEntityEffect;
import protocolsupport.protocol.types.particle.ParticleExplosion;
import protocolsupport.protocol.types.particle.ParticleExplosionEmitter;
import protocolsupport.protocol.types.particle.ParticleFallingDust;
import protocolsupport.protocol.types.particle.ParticleFallingLava;
import protocolsupport.protocol.types.particle.ParticleFallingWater;
import protocolsupport.protocol.types.particle.ParticleFirework;
import protocolsupport.protocol.types.particle.ParticleFishing;
import protocolsupport.protocol.types.particle.ParticleFlame;
import protocolsupport.protocol.types.particle.ParticleFlash;
import protocolsupport.protocol.types.particle.ParticleHappyVillager;
import protocolsupport.protocol.types.particle.ParticleHeart;
import protocolsupport.protocol.types.particle.ParticleInstantEffect;
import protocolsupport.protocol.types.particle.ParticleItem;
import protocolsupport.protocol.types.particle.ParticleItemSlime;
import protocolsupport.protocol.types.particle.ParticleItemSnowball;
import protocolsupport.protocol.types.particle.ParticleLandingLava;
import protocolsupport.protocol.types.particle.ParticleLargeSmoke;
import protocolsupport.protocol.types.particle.ParticleLava;
import protocolsupport.protocol.types.particle.ParticleMycelium;
import protocolsupport.protocol.types.particle.ParticleNote;
import protocolsupport.protocol.types.particle.ParticlePoof;
import protocolsupport.protocol.types.particle.ParticlePortal;
import protocolsupport.protocol.types.particle.ParticleRain;
import protocolsupport.protocol.types.particle.ParticleSmoke;
import protocolsupport.protocol.types.particle.ParticleSneeze;
import protocolsupport.protocol.types.particle.ParticleSpit;
import protocolsupport.protocol.types.particle.ParticleSplash;
import protocolsupport.protocol.types.particle.ParticleSweepAttack;
import protocolsupport.protocol.types.particle.ParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.ParticleUnderwater;
import protocolsupport.protocol.types.particle.ParticleWitch;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ParticleRemapper {

	//TODO: actually move out legacy id remaps to separate class
	public static final RemappingRegistry<ParticleRemappingTable> REGISTRY = new RemappingRegistry<ParticleRemappingTable>() {
		{
			Arrays.stream(ProtocolVersionsHelper.UP_1_13)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
				registerRemap(ParticleBlock.class, original -> new ParticleBlock(
					original.getId(),
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, original.getBlockData())
				), version);
				registerRemap(ParticleFallingDust.class, original -> new ParticleFallingDust(
					original.getId(),
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, original.getBlockData())
				));
				registerRemap(ParticleItem.class, original -> new ParticleItem(
					original.getId(),
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					version, I18NData.DEFAULT_LOCALE, original.getItemStack()
				), version);
			});

			Any<Integer, String> explode = new Any<>(0, "explode");
			registerSimpleLegacyRemap(ParticlePoof.class, explode, ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleDragonBreath.class, explode, ProtocolVersionsHelper.BEFORE_1_9);
			registerSimpleLegacyRemap(ParticleExplosion.class, new Any<>(1, "largeexplode"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleExplosionEmitter.class, new Any<>(2, "hugeexplosion"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleFirework.class, new Any<>(3, "fireworksSpark"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleBubble.class, new Any<>(4, "bubble"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleSplash.class, new Any<>(5, "splash"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleFishing.class, new Any<>(6, "wake"), ProtocolVersionsHelper.RANGE__1_7_5__1_12_2);
			registerSimpleLegacyRemap(ParticleUnderwater.class, new Any<>(7, "suspended"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleCrit.class, new Any<>(9, "crit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleEnchantedHit.class, new Any<>(10, "magicCrit"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleSmoke.class, new Any<>(11, "smoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleLargeSmoke.class, new Any<>(12, "largesmoke"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleEffect.class, new Any<>(13, "spell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleInstantEffect.class, new Any<>(14, "instantSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleEntityEffect.class, new Any<>(15, "mobSpell"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleAmbientEntityEffect.class, new Any<>(16, "mobSpellAmbient"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleWitch.class, new Any<>(17, "witchMagic"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleDrippingWater.class, new Any<>(18, "dripWater"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleDrippingLava.class, new Any<>(19, "dripLava"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleAngryVillager.class, new Any<>(20, "angryVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleHappyVillager.class, new Any<>(21, "happyVillager"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleMycelium.class, new Any<>(22, "townaura"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleNote.class, new Any<>(23, "note"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticlePortal.class, new Any<>(24, "portal"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleEnchant.class, new Any<>(25, "enchantmenttable"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleFlame.class, new Any<>(26, "flame"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleLava.class, new Any<>(27, "lava"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleCloud.class, new Any<>(29, "cloud"), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemap(
				ParticleDust.class,
				original -> new LegacyParticle(
					30, "reddust",
					original.getRed(), original.getGreen(), original.getBlue(),
					1, 0
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerSimpleLegacyRemap(ParticleItemSnowball.class, new Any<>(31, "snowballpoof"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleItemSlime.class, new Any<>(33, "slime"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleHeart.class, new Any<>(34, "heart"), ProtocolVersionsHelper.BEFORE_1_13);
			registerSimpleLegacyRemap(ParticleBarrier.class, new Any<>(35, "barrier"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_13)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemap(
					ParticleItem.class,
					original -> new LegacyParticleIconCrack(
						36, "iconcrack",
						original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
						original.getData(), original.getCount(),
						ItemStackRemapper.remapToClient(version, I18NData.DEFAULT_LOCALE, original.getItemStack())
					),
					version
				);
				registerRemap(
					ParticleBlock.class,
					original -> new LegacyParticleBlockCrack(
						37, "blockcrack",
						original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
						original.getData(), original.getCount(),
						BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, original.getBlockData())
					),
					version
				);
			});
			registerSimpleLegacyRemap(ParticleRain.class, new Any<>(39, "droplet"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerSimpleLegacyRemap(ParticleElderGuardian.class, new Any<>(41, "mobappearance"), ProtocolVersionsHelper.RANGE__1_8__1_12_2);
			registerSimpleLegacyRemap(ParticleDragonBreath.class, new Any<>(42, "dragonbreath"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerSimpleLegacyRemap(ParticleEndRod.class, new Any<>(43, "endRod"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerSimpleLegacyRemap(ParticleDamageIndicator.class, new Any<>(44, "damageIndicator"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			registerSimpleLegacyRemap(ParticleSweepAttack.class, new Any<>(45, "sweepAttack"), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
			Arrays.stream(ProtocolVersionsHelper.RANGE__1_10__1_12_2)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemap(
					ParticleFallingDust.class,
					original -> new LegacyParticleFallingDust(
						46, "fallingdust",
						original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
						original.getData(), original.getCount(),
						BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, original.getBlockData())
					),
					version
				);
			});
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_10)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemap(
					ParticleFallingDust.class,
					original -> new LegacyParticleBlockCrack(
						37, "blockcrack",
						original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
						original.getData(), original.getCount(),
						BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, original.getBlockData())
					),
					version
				);
			});
			registerSimpleLegacyRemap(ParticleTotemOfUndying.class, new Any<>(47, "totem"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);
			registerSimpleLegacyRemap(ParticleSpit.class, new Any<>(48, "spit"), ProtocolVersionsHelper.RANGE__1_11_1__1_12_2);

			//TODO: check if some of those particles can be mapped to another particle
			registerSkip(ParticleFallingLava.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleLandingLava.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleFallingWater.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleFlash.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleComposter.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleSneeze.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleCampfireCozySmoke.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleCampfireSignalSmoke.class, ProtocolVersionsHelper.BEFORE_1_14);
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

		@SuppressWarnings("unchecked")
		protected <T extends Particle> void registerRemap(Class<T> from, Function<T, Particle> remapFunction, ProtocolVersion... versions) {
			Arrays.stream(versions)
			.forEach(version -> getTable(version).setRemap(from, (Function<Particle, Particle>) remapFunction));
		}

		protected <T extends Particle> void registerSimpleLegacyRemap(Class<T> from, Any<Integer, String> legacyParticleInfo, ProtocolVersion... versions) {
			registerRemap(from, original -> new LegacyParticle(
				legacyParticleInfo.getObj1(), legacyParticleInfo.getObj2(),
				original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
				original.getData(), original.getCount()
			), versions);
		}

		protected void registerSkip(Class<? extends Particle> from, ProtocolVersion... versions) {
			registerRemap(from, original -> null, versions);
		}

		@Override
		protected ParticleRemappingTable createTable() {
			return new ParticleRemappingTable();
		}
	};

	public static class ParticleRemappingTable extends RemappingTable {

		protected final Map<Class<? extends Particle>, Function<Particle, Particle>> table = new HashMap<>();

		public void setRemap(Class<? extends Particle> particle, Function<Particle, Particle> remap) {
			table.put(particle, remap);
		}

		/**
		 * Returns a remapping function for provided particle type <br>
		 * Returned remapping function is never null <br>
		 * The remapping function itself however can return new particle or null (which means that particle should be skipped)
		 * @param particle particle type
		 * @return remapping function
		 */
		public Function<Particle, Particle> getRemap(Class<? extends Particle> particle) {
			return table.getOrDefault(particle, part -> part);
		}

	}

}
