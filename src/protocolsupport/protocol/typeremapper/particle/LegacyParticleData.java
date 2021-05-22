package protocolsupport.protocol.typeremapper.particle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.Particle;
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
import protocolsupport.protocol.types.particle.types.ParticleCurrentDown;
import protocolsupport.protocol.types.particle.types.ParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.ParticleDolphin;
import protocolsupport.protocol.types.particle.types.ParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.ParticleDrippingHoney;
import protocolsupport.protocol.types.particle.types.ParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.ParticleDrippingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.ParticleDust;
import protocolsupport.protocol.types.particle.types.ParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.ParticleEndRod;
import protocolsupport.protocol.types.particle.types.ParticleFallingDust;
import protocolsupport.protocol.types.particle.types.ParticleFallingHoney;
import protocolsupport.protocol.types.particle.types.ParticleFallingLava;
import protocolsupport.protocol.types.particle.types.ParticleFallingNectar;
import protocolsupport.protocol.types.particle.types.ParticleFallingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleFallingWater;
import protocolsupport.protocol.types.particle.types.ParticleFishing;
import protocolsupport.protocol.types.particle.types.ParticleFlame;
import protocolsupport.protocol.types.particle.types.ParticleFlash;
import protocolsupport.protocol.types.particle.types.ParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.ParticleItem;
import protocolsupport.protocol.types.particle.types.ParticleLandingHoney;
import protocolsupport.protocol.types.particle.types.ParticleLandingLava;
import protocolsupport.protocol.types.particle.types.ParticleLandingObsidianTear;
import protocolsupport.protocol.types.particle.types.ParticleNautilus;
import protocolsupport.protocol.types.particle.types.ParticlePoof;
import protocolsupport.protocol.types.particle.types.ParticlePortal;
import protocolsupport.protocol.types.particle.types.ParticleRain;
import protocolsupport.protocol.types.particle.types.ParticleReversePortal;
import protocolsupport.protocol.types.particle.types.ParticleSneeze;
import protocolsupport.protocol.types.particle.types.ParticleSoul;
import protocolsupport.protocol.types.particle.types.ParticleSoulFlame;
import protocolsupport.protocol.types.particle.types.ParticleSpit;
import protocolsupport.protocol.types.particle.types.ParticleSplash;
import protocolsupport.protocol.types.particle.types.ParticleSquidInk;
import protocolsupport.protocol.types.particle.types.ParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.ParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.ParticleWarpedSpore;
import protocolsupport.protocol.types.particle.types.ParticleWhiteAsh;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyParticleData {

	//TODO: single -> multiple particles
	public static final MappingRegistry<LegacyParticleDataTable> REGISTRY = new MappingRegistry<LegacyParticleDataTable>() {
		{
			Arrays.stream(ProtocolVersionsHelper.ALL)
			.forEach(version -> {
				ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				register(ParticleBlock.class, original -> new ParticleBlock(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockDataRemappingTable.get(original.getBlockData())
				), version);
				register(ParticleFallingDust.class, original -> new ParticleFallingDust(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockDataRemappingTable.get(original.getBlockData())
				), version);
				register(ParticleItem.class, original -> new ParticleItem(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					ItemStackRemappingHelper.toLegacyItemData(version, I18NData.DEFAULT_LOCALE, original.getItemStack())
				), version);
			});

			register(
				ParticleSoulFlame.class,
				original -> new ParticleFlame(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_15_2
			);
			register(
				ParticleReversePortal.class,
				original -> new ParticlePortal(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_15_2
			);

			register(
				ParticleFallingLava.class,
				original -> new ParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				ParticleLandingLava.class,
				original -> new ParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				ParticleFallingWater.class,
				original -> new ParticleDrippingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				ParticleComposter.class,
				original -> new ParticleHappyVillager(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				ParticleSneeze.class,
				original -> new ParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			); //TODO: map to colored dust instead
			register(
				ParticleDragonBreath.class,
				original -> new ParticlePoof(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleDust.class,
				original -> new ParticleDust(
					original.getRed(), original.getGreen(), original.getBlue(), 1F, 0,
					0, 0, 0, 0
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleSquidInk.class,
				original -> new ParticleDust(
					0, 0, 0, 1F, 0,
					0, 0, 0, 0
				), ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleBubblePop.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleCurrentDown.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleBubbleColumnUp.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				ParticleSpit.class,
				original -> new ParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_10
			);
			register(
				ParticleRain.class,
				original -> new ParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_7_10
			);
			register(
				ParticleFishing.class,
				original -> new ParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_6_4
			);

			//TODO: think of possible mappings
			registerSkip(ParticleSoul.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleAsh.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleWhiteAsh.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleCrimsonSpore.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleWarpedSpore.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleDrippingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleFallingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleLandingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(ParticleDrippingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(ParticleFallingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(ParticleLandingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(ParticleFallingNectar.class, ProtocolVersionsHelper.DOWN_1_14_4);

			registerSkip(ParticleFlash.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(ParticleCampfireCozySmoke.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(ParticleCampfireSignalSmoke.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(ParticleNautilus.class, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkip(ParticleDolphin.class, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkip(ParticleTotemOfUndying.class, ProtocolVersionsHelper.DOWN_1_10);
			registerSkip(ParticleFallingDust.class, ProtocolVersionsHelper.DOWN_1_9_4); //TODO: map to colored dust, after building blockdata -> color table
			registerSkip(ParticleEndRod.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(ParticleDamageIndicator.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(ParticleSweepAttack.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(ParticleElderGuardian.class, ProtocolVersionsHelper.DOWN_1_7_10);
			registerSkip(ParticleBarrier.class, ProtocolVersionsHelper.DOWN_1_7_10);
		}

		@SuppressWarnings("unchecked")
		protected <T extends Particle> void register(Class<T> from, Function<T, Particle> remapFunction, ProtocolVersion... versions) {
			Arrays.stream(versions)
			.forEach(version -> getTable(version).set(from, (Function<Particle, Particle>) remapFunction));
		}

		protected void registerSkip(Class<? extends Particle> from, ProtocolVersion... versions) {
			register(from, original -> null, versions);
		}

		@Override
		protected LegacyParticleDataTable createTable() {
			return new LegacyParticleDataTable();
		}

	};

	public static class LegacyParticleDataTable extends MappingTable {

		protected final Map<Class<? extends Particle>, Function<Particle, Particle>> table = new HashMap<>();

		public void set(Class<? extends Particle> particle, Function<Particle, Particle> remap) {
			table.put(particle, remap);
		}

		/**
		 * Returns a mapping function for provided particle type <br>
		 * The mapping function itself can return new particle or null (which means that particle should be skipped)
		 * @param particle particle type
		 * @return mapping function
		 */
		public Function<Particle, Particle> get(Class<? extends Particle> particle) {
			Function<Particle, Particle> function = table.get(particle);
			return function != null ? function : UnaryOperator.identity();
		}

	}

}
