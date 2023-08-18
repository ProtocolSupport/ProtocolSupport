package protocolsupport.protocol.typeremapper.particle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.types.NetworkParticleAsh;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlock;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlockMarker;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubble;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubbleColumnUp;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubblePop;
import protocolsupport.protocol.types.particle.types.NetworkParticleCampfireCozySmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleCampfireSignalSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleCherryLeaves;
import protocolsupport.protocol.types.particle.types.NetworkParticleCloud;
import protocolsupport.protocol.types.particle.types.NetworkParticleComposter;
import protocolsupport.protocol.types.particle.types.NetworkParticleCrimsonSpore;
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
import protocolsupport.protocol.types.particle.types.NetworkParticleEggCrack;
import protocolsupport.protocol.types.particle.types.NetworkParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.NetworkParticleElectricSpark;
import protocolsupport.protocol.types.particle.types.NetworkParticleEndRod;
import protocolsupport.protocol.types.particle.types.NetworkParticleExplosion;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingBlossom;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDripstoneLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDripstoneWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingHoney;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingNectar;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingObsidianTear;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleFishing;
import protocolsupport.protocol.types.particle.types.NetworkParticleFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleFlash;
import protocolsupport.protocol.types.particle.types.NetworkParticleGlow;
import protocolsupport.protocol.types.particle.types.NetworkParticleGlowSquidInk;
import protocolsupport.protocol.types.particle.types.NetworkParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleItem;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingHoney;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleLandingObsidianTear;
import protocolsupport.protocol.types.particle.types.NetworkParticleNautilus;
import protocolsupport.protocol.types.particle.types.NetworkParticlePoof;
import protocolsupport.protocol.types.particle.types.NetworkParticlePortal;
import protocolsupport.protocol.types.particle.types.NetworkParticleRain;
import protocolsupport.protocol.types.particle.types.NetworkParticleReversePortal;
import protocolsupport.protocol.types.particle.types.NetworkParticleScrape;
import protocolsupport.protocol.types.particle.types.NetworkParticleSculkCharge;
import protocolsupport.protocol.types.particle.types.NetworkParticleSculkChargePop;
import protocolsupport.protocol.types.particle.types.NetworkParticleSculkSoul;
import protocolsupport.protocol.types.particle.types.NetworkParticleShriek;
import protocolsupport.protocol.types.particle.types.NetworkParticleSmallFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleSneeze;
import protocolsupport.protocol.types.particle.types.NetworkParticleSnowflake;
import protocolsupport.protocol.types.particle.types.NetworkParticleSonicBoom;
import protocolsupport.protocol.types.particle.types.NetworkParticleSoul;
import protocolsupport.protocol.types.particle.types.NetworkParticleSoulFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleSpit;
import protocolsupport.protocol.types.particle.types.NetworkParticleSplash;
import protocolsupport.protocol.types.particle.types.NetworkParticleSporeBlossomAir;
import protocolsupport.protocol.types.particle.types.NetworkParticleSquidInk;
import protocolsupport.protocol.types.particle.types.NetworkParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.NetworkParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.NetworkParticleVibration;
import protocolsupport.protocol.types.particle.types.NetworkParticleWarpedSpore;
import protocolsupport.protocol.types.particle.types.NetworkParticleWaxOff;
import protocolsupport.protocol.types.particle.types.NetworkParticleWaxOn;
import protocolsupport.protocol.types.particle.types.NetworkParticleWhiteAsh;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkParticleLegacyData {

	private NetworkParticleLegacyData() {
	}

	//TODO: single -> multiple particles
	public static final MappingRegistry<NetworkParticleLegacyDataTable> REGISTRY = new NetworkPartcleLegacyDataRegistry();

	public static class NetworkPartcleLegacyDataRegistry extends MappingRegistry<NetworkParticleLegacyDataTable> {

		protected NetworkPartcleLegacyDataRegistry() {
			for (ProtocolVersion version : ProtocolVersionsHelper.UP_1_18) {
				ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
				register(NetworkParticleBlockMarker.class, original -> new NetworkParticleBlockMarker(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockLegacyDataTable.get(original.getBlockData())
				), version);
			}

			for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
				ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
				register(NetworkParticleBlock.class, original -> new NetworkParticleBlock(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockLegacyDataTable.get(original.getBlockData())
				), version);
				register(NetworkParticleFallingDust.class, original -> new NetworkParticleFallingDust(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					blockLegacyDataTable.get(original.getBlockData())
				), version);
				register(NetworkParticleItem.class, original -> new NetworkParticleItem(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					ItemStackRemappingHelper.toLegacyItemData(version, I18NData.DEFAULT_LOCALE, original.getItemStack())
				), version);
			}

			register(
				NetworkParticleEggCrack.class,
				original -> new NetworkParticleHappyVillager(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_19_4
			);

			register(
				NetworkParticleSonicBoom.class,
				original -> new NetworkParticleExplosion(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_18_2
			);
			register(
				NetworkParticleShriek.class,
				original -> new NetworkParticlePoof(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_18_2
			);
			register(
				NetworkParticleSculkSoul.class,
				original -> new NetworkParticleSoul(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.RANGE__1_16__1_18_2
			);
			registerSkip(NetworkParticleSculkSoul.class, ProtocolVersionsHelper.DOWN_1_15_2);
			register(
				NetworkParticleSculkCharge.class,
				original -> new NetworkParticleSoul(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.RANGE__1_16__1_18_2
			);
			registerSkip(NetworkParticleSculkCharge.class, ProtocolVersionsHelper.DOWN_1_15_2);
			register(
				NetworkParticleSculkChargePop.class,
				original -> new NetworkParticleSoul(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.RANGE__1_16__1_18_2
			);
			registerSkip(NetworkParticleSculkChargePop.class, ProtocolVersionsHelper.DOWN_1_15_2);

			register(
				NetworkParticleDustTransition.class,
				original -> new NetworkParticleDust(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount(),
					original.getTargetRed(), original.getTargetGreen(), original.getTargetBlue(), original.getScale()
				),
				ProtocolVersionsHelper.DOWN_1_16_4
			);
			register(
				NetworkParticleDrippingDripstoneLava.class,
				original -> new NetworkParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_16_4
			);
			register(
				NetworkParticleDrippingDripstoneWater.class,
				original -> new NetworkParticleDrippingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_16_4
			);
			register(
				NetworkParticleFallingDripstoneLava.class,
				original -> new NetworkParticleFallingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.RANGE__1_14__1_16_4
			);
			register(
				NetworkParticleFallingDripstoneLava.class,
				original -> new NetworkParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleFallingDripstoneWater.class,
				original -> new NetworkParticleFallingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.RANGE__1_14__1_16_4
			);
			register(
				NetworkParticleFallingDripstoneWater.class,
				original -> new NetworkParticleDrippingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleSmallFlame.class,
				original -> new NetworkParticleFlame(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_16_4
			);

			register(
				NetworkParticleSoulFlame.class,
				original -> new NetworkParticleFlame(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_15_2
			);
			register(
				NetworkParticleReversePortal.class,
				original -> new NetworkParticlePortal(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_15_2
			);

			register(
				NetworkParticleFallingLava.class,
				original -> new NetworkParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleLandingLava.class,
				original -> new NetworkParticleDrippingLava(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleFallingWater.class,
				original -> new NetworkParticleDrippingWater(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleComposter.class,
				original -> new NetworkParticleHappyVillager(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			);
			register(
				NetworkParticleSneeze.class,
				original -> new NetworkParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_13_2
			); //TODO: map to colored dust instead
			register(
				NetworkParticleDragonBreath.class,
				original -> new NetworkParticlePoof(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleDust.class,
				original -> new NetworkParticleDust(
					original.getRed(), original.getGreen(), original.getBlue(), 1F, 0,
					0, 0, 0, 0
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleSquidInk.class,
				original -> new NetworkParticleDust(
					0, 0, 0, 1F, 0,
					0, 0, 0, 0
				), ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleBubblePop.class,
				original -> new NetworkParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleCurrentDown.class,
				original -> new NetworkParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleBubbleColumnUp.class,
				original -> new NetworkParticleBubble(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_12_2
			);
			register(
				NetworkParticleSpit.class,
				original -> new NetworkParticleCloud(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_10
			);
			register(
				NetworkParticleRain.class,
				original -> new NetworkParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_7_10
			);
			register(
				NetworkParticleFishing.class,
				original -> new NetworkParticleSplash(
					original.getOffsetX(), original.getOffsetY(), original.getOffsetZ(),
					original.getData(), original.getCount()
				),
				ProtocolVersionsHelper.DOWN_1_6_4
			);

			//TODO: think of possible mappings
			registerSkip(NetworkParticleCherryLeaves.class, ProtocolVersionsHelper.DOWN_1_19_4);
			registerSkip(NetworkParticleFallingBlossom.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleSporeBlossomAir.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleSnowflake.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleWaxOn.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleWaxOff.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleGlowSquidInk.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleGlow.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleElectricSpark.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleScrape.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleSoul.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleAsh.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleWhiteAsh.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleCrimsonSpore.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleWarpedSpore.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleDrippingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleFallingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleLandingObsidianTear.class, ProtocolVersionsHelper.DOWN_1_15_2);
			registerSkip(NetworkParticleDrippingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(NetworkParticleFallingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(NetworkParticleLandingHoney.class, ProtocolVersionsHelper.DOWN_1_14_4);
			registerSkip(NetworkParticleFallingNectar.class, ProtocolVersionsHelper.DOWN_1_14_4);

			registerSkip(NetworkParticleVibration.class, ProtocolVersionsHelper.DOWN_1_16_4);
			registerSkip(NetworkParticleFlash.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(NetworkParticleCampfireCozySmoke.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(NetworkParticleCampfireSignalSmoke.class, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkip(NetworkParticleNautilus.class, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkip(NetworkParticleDolphin.class, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkip(NetworkParticleTotemOfUndying.class, ProtocolVersionsHelper.DOWN_1_10);
			registerSkip(NetworkParticleFallingDust.class, ProtocolVersionsHelper.DOWN_1_9_4); //TODO: map to colored dust, after building blockdata -> color table
			registerSkip(NetworkParticleEndRod.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(NetworkParticleDamageIndicator.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(NetworkParticleSweepAttack.class, ProtocolVersionsHelper.DOWN_1_8);
			registerSkip(NetworkParticleElderGuardian.class, ProtocolVersionsHelper.DOWN_1_7_10);
			registerSkip(NetworkParticleBlockMarker.class, ProtocolVersionsHelper.DOWN_1_7_10);
		}

		@SuppressWarnings("unchecked")
		protected <T extends NetworkParticle> void register(Class<T> from, Function<T, NetworkParticle> remapFunction, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(from, (Function<NetworkParticle, NetworkParticle>) remapFunction);
			}
		}

		protected void registerSkip(Class<? extends NetworkParticle> from, ProtocolVersion... versions) {
			register(from, original -> null, versions);
		}

		@Override
		protected NetworkParticleLegacyDataTable createTable() {
			return new NetworkParticleLegacyDataTable();
		}

	}

	public static class NetworkParticleLegacyDataTable extends MappingTable {

		protected final Map<Class<? extends NetworkParticle>, Function<NetworkParticle, NetworkParticle>> table = new HashMap<>();

		public void set(Class<? extends NetworkParticle> particle, Function<NetworkParticle, NetworkParticle> remap) {
			table.put(particle, remap);
		}

		/**
		 * Returns a mapping function for provided particle type <br>
		 * The mapping function itself can return new particle or null (which means that particle should be skipped)
		 * @param particle particle type
		 * @return mapping function
		 */
		public Function<NetworkParticle, NetworkParticle> get(Class<? extends NetworkParticle> particle) {
			Function<NetworkParticle, NetworkParticle> function = table.get(particle);
			return function != null ? function : UnaryOperator.identity();
		}

	}

}
