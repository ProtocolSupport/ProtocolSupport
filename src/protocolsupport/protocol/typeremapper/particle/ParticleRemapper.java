package protocolsupport.protocol.typeremapper.particle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.types.ParticleBarrier;
import protocolsupport.protocol.types.particle.types.ParticleBlock;
import protocolsupport.protocol.types.particle.types.ParticleBubble;
import protocolsupport.protocol.types.particle.types.ParticleBubbleColumnUp;
import protocolsupport.protocol.types.particle.types.ParticleBubblePop;
import protocolsupport.protocol.types.particle.types.ParticleCampfireCozySmoke;
import protocolsupport.protocol.types.particle.types.ParticleCampfireSignalSmoke;
import protocolsupport.protocol.types.particle.types.ParticleCloud;
import protocolsupport.protocol.types.particle.types.ParticleComposter;
import protocolsupport.protocol.types.particle.types.ParticleCurrentDown;
import protocolsupport.protocol.types.particle.types.ParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.ParticleDolphin;
import protocolsupport.protocol.types.particle.types.ParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.ParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.ParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.ParticleDust;
import protocolsupport.protocol.types.particle.types.ParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.ParticleEndRod;
import protocolsupport.protocol.types.particle.types.ParticleFallingDust;
import protocolsupport.protocol.types.particle.types.ParticleFallingLava;
import protocolsupport.protocol.types.particle.types.ParticleFallingWater;
import protocolsupport.protocol.types.particle.types.ParticleFishing;
import protocolsupport.protocol.types.particle.types.ParticleFlash;
import protocolsupport.protocol.types.particle.types.ParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.ParticleItem;
import protocolsupport.protocol.types.particle.types.ParticleLandingLava;
import protocolsupport.protocol.types.particle.types.ParticleNautilus;
import protocolsupport.protocol.types.particle.types.ParticlePoof;
import protocolsupport.protocol.types.particle.types.ParticleRain;
import protocolsupport.protocol.types.particle.types.ParticleSneeze;
import protocolsupport.protocol.types.particle.types.ParticleSpit;
import protocolsupport.protocol.types.particle.types.ParticleSplash;
import protocolsupport.protocol.types.particle.types.ParticleSquidInk;
import protocolsupport.protocol.types.particle.types.ParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.ParticleTotemOfUndying;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ParticleRemapper {

	//TODO: single -> multiple particles
	public static final RemappingRegistry<ParticleRemappingTable> REGISTRY = new RemappingRegistry<ParticleRemappingTable>() {
		{
			Arrays.stream(ProtocolVersion.getAllSupported())
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemap(ParticleBlock.class, original -> new ParticleBlock(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockDataRemappingTable.getRemap(original.getBlockData())
				), version);
				registerRemap(ParticleFallingDust.class, original -> new ParticleFallingDust(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockDataRemappingTable.getRemap(original.getBlockData())
				));
				registerRemap(ParticleItem.class, original -> new ParticleItem(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					ItemStackRemapper.remapToClient(version, I18NData.DEFAULT_LOCALE, original.getItemStack())
				));
			});

			registerRemap(
				ParticleFallingLava.class,
				original -> new ParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_14
			);
			registerRemap(
				ParticleLandingLava.class,
				original -> new ParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_14
			);
			registerRemap(
				ParticleFallingWater.class,
				original -> new ParticleDrippingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_14
			);
			registerRemap(
				ParticleComposter.class,
				original -> new ParticleHappyVillager(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_14
			);
			registerRemap(
				ParticleSneeze.class,
				original -> new ParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_14
			); //TODO: remap to colored dust instead
			registerRemap(
				ParticleDragonBreath.class,
				original -> new ParticlePoof(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleDust.class,
				original -> new ParticleDust(
					original.getRed(), original.getGreen(), original.getBlue(), 1F, 0,
					0, 0, 0, 0
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleSquidInk.class,
				original -> new ParticleDust(
					0, 0, 0, 1F, 0,
					0, 0, 0, 0
				), ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleBubblePop.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleCurrentDown.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleBubbleColumnUp.class,
				original -> new ParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemap(
				ParticleSpit.class,
				original -> new ParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_11
			);
			registerRemap(
				ParticleRain.class,
				original -> new ParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			registerRemap(
				ParticleFishing.class,
				original -> new ParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.BEFORE_1_7
			);

			registerSkip(ParticleFlash.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleCampfireCozySmoke.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleCampfireSignalSmoke.class, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkip(ParticleNautilus.class, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkip(ParticleDolphin.class, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkip(ParticleTotemOfUndying.class, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkip(ParticleFallingDust.class, ProtocolVersionsHelper.BEFORE_1_10); //TODO: actually remap to colored dust, after building blockdata -> color table
			registerSkip(ParticleEndRod.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleDamageIndicator.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleSweepAttack.class, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkip(ParticleElderGuardian.class, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkip(ParticleBarrier.class, ProtocolVersionsHelper.BEFORE_1_8);
		}

		@SuppressWarnings("unchecked")
		protected <T extends Particle> void registerRemap(Class<T> from, Function<T, Particle> remapFunction, ProtocolVersion... versions) {
			Arrays.stream(versions)
			.forEach(version -> getTable(version).setRemap(from, (Function<Particle, Particle>) remapFunction));
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
			Function<Particle, Particle> function = table.get(particle);
			return function != null ? function : UnaryOperator.identity();
		}

	}

}
