package protocolsupport.protocol.typeremapper.legacy;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.types.ParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.types.ParticleAngryVillager;
import protocolsupport.protocol.types.particle.types.ParticleBarrier;
import protocolsupport.protocol.types.particle.types.ParticleBlock;
import protocolsupport.protocol.types.particle.types.ParticleBubble;
import protocolsupport.protocol.types.particle.types.ParticleCloud;
import protocolsupport.protocol.types.particle.types.ParticleCrit;
import protocolsupport.protocol.types.particle.types.ParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.ParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.ParticleDrippingLava;
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
import protocolsupport.protocol.types.particle.types.ParticleFirework;
import protocolsupport.protocol.types.particle.types.ParticleFishing;
import protocolsupport.protocol.types.particle.types.ParticleFlame;
import protocolsupport.protocol.types.particle.types.ParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.ParticleHeart;
import protocolsupport.protocol.types.particle.types.ParticleInstantEffect;
import protocolsupport.protocol.types.particle.types.ParticleItem;
import protocolsupport.protocol.types.particle.types.ParticleItemSlime;
import protocolsupport.protocol.types.particle.types.ParticleItemSnowball;
import protocolsupport.protocol.types.particle.types.ParticleLargeSmoke;
import protocolsupport.protocol.types.particle.types.ParticleLava;
import protocolsupport.protocol.types.particle.types.ParticleMycelium;
import protocolsupport.protocol.types.particle.types.ParticleNote;
import protocolsupport.protocol.types.particle.types.ParticlePoof;
import protocolsupport.protocol.types.particle.types.ParticlePortal;
import protocolsupport.protocol.types.particle.types.ParticleRain;
import protocolsupport.protocol.types.particle.types.ParticleSmoke;
import protocolsupport.protocol.types.particle.types.ParticleSpit;
import protocolsupport.protocol.types.particle.types.ParticleSplash;
import protocolsupport.protocol.types.particle.types.ParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.ParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.ParticleUnderwater;
import protocolsupport.protocol.types.particle.types.ParticleWitch;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyParticle {

	public static class IntId {

		protected static final Object2IntMap<Class<? extends Particle>> classToId = new Object2IntOpenHashMap<>();

		static {
			classToId.defaultReturnValue(-1);
			classToId.put(ParticlePoof.class, 0);
			classToId.put(ParticleExplosion.class, 1);
			classToId.put(ParticleExplosionEmitter.class, 2);
			classToId.put(ParticleFirework.class, 3);
			classToId.put(ParticleBubble.class, 4);
			classToId.put(ParticleSplash.class, 5);
			classToId.put(ParticleFishing.class, 6);
			classToId.put(ParticleUnderwater.class, 7);
			classToId.put(ParticleCrit.class, 9);
			classToId.put(ParticleEnchantedHit.class, 10);
			classToId.put(ParticleSmoke.class, 11);
			classToId.put(ParticleLargeSmoke.class, 12);
			classToId.put(ParticleEffect.class, 13);
			classToId.put(ParticleInstantEffect.class, 14);
			classToId.put(ParticleEntityEffect.class, 15);
			classToId.put(ParticleAmbientEntityEffect.class, 16);
			classToId.put(ParticleWitch.class, 17);
			classToId.put(ParticleDrippingWater.class, 18);
			classToId.put(ParticleDrippingLava.class, 19);
			classToId.put(ParticleAngryVillager.class, 20);
			classToId.put(ParticleHappyVillager.class, 21);
			classToId.put(ParticleMycelium.class, 22);
			classToId.put(ParticleNote.class, 23);
			classToId.put(ParticlePortal.class, 24);
			classToId.put(ParticleEnchant.class, 25);
			classToId.put(ParticleFlame.class, 26);
			classToId.put(ParticleLava.class, 27);
			classToId.put(ParticleCloud.class, 29);
			classToId.put(ParticleDust.class, 30);
			classToId.put(ParticleItemSnowball.class, 31);
			classToId.put(ParticleItemSlime.class, 33);
			classToId.put(ParticleHeart.class, 34);
			classToId.put(ParticleBarrier.class, 35);
			classToId.put(ParticleItem.class, 36);
			classToId.put(ParticleBlock.class, 37);
			classToId.put(ParticleRain.class, 39);
			classToId.put(ParticleElderGuardian.class, 41);
			classToId.put(ParticleDragonBreath.class, 42);
			classToId.put(ParticleEndRod.class, 43);
			classToId.put(ParticleDamageIndicator.class, 44);
			classToId.put(ParticleSweepAttack.class, 45);
			classToId.put(ParticleFallingDust.class, 46);
			classToId.put(ParticleTotemOfUndying.class, 47);
			classToId.put(ParticleSpit.class, 48);
		}

		protected static final CachedInstanceOfChain<BiFunction<ProtocolVersion, Particle, int[]>> toData = new CachedInstanceOfChain<>();
		protected static final int[] data_none = new int[0];

		protected static <L extends Particle> void register(Class<L> clazz, Function<L, int[]> function) {
			register(clazz, (protocol, particle) -> function.apply(particle));
		}

		@SuppressWarnings("unchecked")
		protected static <L extends Particle> void register(Class<L> clazz, BiFunction<ProtocolVersion, L, int[]> function) {
			toData.setKnownPath(clazz, (BiFunction<ProtocolVersion, Particle, int[]>) function);
		}

		static {
			register(Particle.class, particle -> data_none);
			register(ParticleItem.class, (version, particle) -> {
				NetworkItemStack itemstack = ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack());
				return new int[] { itemstack.getTypeId(), itemstack.getLegacyData() };
			});
			register(ParticleBlock.class, particle -> new int[] {
				PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(particle.getBlockData()))
			});
			register(ParticleFallingDust.class, particle -> new int[] {
				PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(particle.getBlockData()))
			});
		}

		public static int getId(Particle particle) {
			int id = classToId.getInt(particle.getClass());
			if (id == -1) {
				throw new IllegalArgumentException(MessageFormat.format("No legacy id exists for particle {0}", particle.getClass()));
			}
			return id;
		}

		public static int[] getData(ProtocolVersion version, Particle particle) {
			return toData.selectPath(particle.getClass()).apply(version, particle);
		}

	}

	public static class StringId {

		protected static final Map<Class<Particle>, BiFunction<ProtocolVersion, Particle, String>> toIdData = new HashMap<>();

		protected static <L extends Particle> void register(Class<L> clazz, Function<L, String> function) {
			register(clazz, (protocol, particle) -> function.apply(particle));
		}

		@SuppressWarnings("unchecked")
		protected static <L extends Particle> void register(Class<L> clazz, BiFunction<ProtocolVersion, L, String> function) {
			toIdData.put((Class<Particle>) clazz, (BiFunction<ProtocolVersion, Particle, String>) function);
		}

		static {
			register(ParticlePoof.class, particle -> "explode");
			register(ParticleExplosion.class, particle -> "largeexplode");
			register(ParticleExplosionEmitter.class, particle -> "hugeexplosion");
			register(ParticleFirework.class, particle -> "fireworksSpark");
			register(ParticleBubble.class, particle -> "bubble");
			register(ParticleSplash.class, particle -> "splash");
			register(ParticleFishing.class, particle -> "wake");
			register(ParticleUnderwater.class, particle -> "suspended");
			register(ParticleCrit.class, particle -> "crit");
			register(ParticleEnchantedHit.class, particle -> "magicCrit");
			register(ParticleSmoke.class, particle -> "smoke");
			register(ParticleLargeSmoke.class, particle -> "largesmoke");
			register(ParticleEffect.class, particle -> "spell");
			register(ParticleInstantEffect.class, particle -> "instantSpell");
			register(ParticleEntityEffect.class, particle -> "mobSpell");
			register(ParticleAmbientEntityEffect.class, particle -> "mobSpellAmbient");
			register(ParticleWitch.class, particle -> "witchMagic");
			register(ParticleDrippingWater.class, particle -> "dripWater");
			register(ParticleDrippingLava.class, particle -> "dripLava");
			register(ParticleAngryVillager.class, particle -> "angryVillager");
			register(ParticleHappyVillager.class, particle -> "happyVillager");
			register(ParticleMycelium.class, particle -> "townaura");
			register(ParticleNote.class, particle -> "note");
			register(ParticlePortal.class, particle -> "portal");
			register(ParticleEnchant.class, particle -> "enchantmenttable");
			register(ParticleFlame.class, particle -> "flame");
			register(ParticleLava.class, particle -> "lava");
			register(ParticleCloud.class, particle -> "cloud");
			register(ParticleDust.class, particle -> "reddust");
			register(ParticleItemSnowball.class, particle -> "snowballpoof");
			register(ParticleItemSlime.class, particle -> "slime");
			register(ParticleHeart.class, particle -> "heart");
			register(ParticleItem.class, (version, particle) -> {
				NetworkItemStack itemstack = ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack());
				return "iconcrack_" + itemstack.getTypeId() + "_" + itemstack.getLegacyData();
			});
			register(ParticleBlock.class, particle -> {
				int lBlockData = PreFlatteningBlockIdData.getCombinedId(particle.getBlockData());
				return "blockcrack_" + PreFlatteningBlockIdData.getIdFromCombinedId(lBlockData) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(lBlockData);
			});
			register(ParticleFallingDust.class, particle -> {
				int lBlockData = PreFlatteningBlockIdData.getCombinedId(particle.getBlockData());
				return "blockdust_" + PreFlatteningBlockIdData.getIdFromCombinedId(lBlockData) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(lBlockData);
			});
		}

		public static String getIdData(ProtocolVersion version, Particle particle) {
			BiFunction<ProtocolVersion, Particle, String> function = toIdData.get(particle.getClass());
			if (function == null) {
				throw new IllegalArgumentException(MessageFormat.format("No legacy id exists for particle {0}", particle.getClass()));
			}
			return function.apply(version, particle);
		}

	}

}
