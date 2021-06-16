package protocolsupport.protocol.typeremapper.particle;

import java.text.MessageFormat;
import java.util.function.BiFunction;
import java.util.function.Function;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.types.NetworkParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleAngryVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleBarrier;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlock;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubble;
import protocolsupport.protocol.types.particle.types.NetworkParticleCloud;
import protocolsupport.protocol.types.particle.types.NetworkParticleCrit;
import protocolsupport.protocol.types.particle.types.NetworkParticleDamageIndicator;
import protocolsupport.protocol.types.particle.types.NetworkParticleDragonBreath;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleElderGuardian;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchant;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchantedHit;
import protocolsupport.protocol.types.particle.types.NetworkParticleEndRod;
import protocolsupport.protocol.types.particle.types.NetworkParticleEntityEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleExplosion;
import protocolsupport.protocol.types.particle.types.NetworkParticleExplosionEmitter;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleFirework;
import protocolsupport.protocol.types.particle.types.NetworkParticleFishing;
import protocolsupport.protocol.types.particle.types.NetworkParticleFlame;
import protocolsupport.protocol.types.particle.types.NetworkParticleHappyVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleHeart;
import protocolsupport.protocol.types.particle.types.NetworkParticleInstantEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleItem;
import protocolsupport.protocol.types.particle.types.NetworkParticleItemSlime;
import protocolsupport.protocol.types.particle.types.NetworkParticleItemSnowball;
import protocolsupport.protocol.types.particle.types.NetworkParticleLargeSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleMycelium;
import protocolsupport.protocol.types.particle.types.NetworkParticleNote;
import protocolsupport.protocol.types.particle.types.NetworkParticlePoof;
import protocolsupport.protocol.types.particle.types.NetworkParticlePortal;
import protocolsupport.protocol.types.particle.types.NetworkParticleRain;
import protocolsupport.protocol.types.particle.types.NetworkParticleSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleSpit;
import protocolsupport.protocol.types.particle.types.NetworkParticleSplash;
import protocolsupport.protocol.types.particle.types.NetworkParticleSweepAttack;
import protocolsupport.protocol.types.particle.types.NetworkParticleTotemOfUndying;
import protocolsupport.protocol.types.particle.types.NetworkParticleUnderwater;
import protocolsupport.protocol.types.particle.types.NetworkParticleWitch;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PreFlatteningNetworkParticleIntIdRegistryDataSerializer {

	private PreFlatteningNetworkParticleIntIdRegistryDataSerializer() {
	}

	private static final Object2IntMap<Class<? extends NetworkParticle>> classToId = new Object2IntOpenHashMap<>();
	private static final CachedInstanceOfChain<BiFunction<ProtocolVersion, NetworkParticle, int[]>> toData = new CachedInstanceOfChain<>();
	private static final int[] data_none = new int[0];

	private static <L extends NetworkParticle> void register(Class<L> clazz, Function<L, int[]> function) {
		register(clazz, (protocol, particle) -> function.apply(particle));
	}

	@SuppressWarnings("unchecked")
	private static <L extends NetworkParticle> void register(Class<L> clazz, BiFunction<ProtocolVersion, L, int[]> function) {
		toData.setKnownPath(clazz, (BiFunction<ProtocolVersion, NetworkParticle, int[]>) function);
	}

	static {
		classToId.defaultReturnValue(-1);
		classToId.put(NetworkParticlePoof.class, 0);
		classToId.put(NetworkParticleExplosion.class, 1);
		classToId.put(NetworkParticleExplosionEmitter.class, 2);
		classToId.put(NetworkParticleFirework.class, 3);
		classToId.put(NetworkParticleBubble.class, 4);
		classToId.put(NetworkParticleSplash.class, 5);
		classToId.put(NetworkParticleFishing.class, 6);
		classToId.put(NetworkParticleUnderwater.class, 7);
		classToId.put(NetworkParticleCrit.class, 9);
		classToId.put(NetworkParticleEnchantedHit.class, 10);
		classToId.put(NetworkParticleSmoke.class, 11);
		classToId.put(NetworkParticleLargeSmoke.class, 12);
		classToId.put(NetworkParticleEffect.class, 13);
		classToId.put(NetworkParticleInstantEffect.class, 14);
		classToId.put(NetworkParticleEntityEffect.class, 15);
		classToId.put(NetworkParticleAmbientEntityEffect.class, 16);
		classToId.put(NetworkParticleWitch.class, 17);
		classToId.put(NetworkParticleDrippingWater.class, 18);
		classToId.put(NetworkParticleDrippingLava.class, 19);
		classToId.put(NetworkParticleAngryVillager.class, 20);
		classToId.put(NetworkParticleHappyVillager.class, 21);
		classToId.put(NetworkParticleMycelium.class, 22);
		classToId.put(NetworkParticleNote.class, 23);
		classToId.put(NetworkParticlePortal.class, 24);
		classToId.put(NetworkParticleEnchant.class, 25);
		classToId.put(NetworkParticleFlame.class, 26);
		classToId.put(NetworkParticleLava.class, 27);
		classToId.put(NetworkParticleCloud.class, 29);
		classToId.put(NetworkParticleDust.class, 30);
		classToId.put(NetworkParticleItemSnowball.class, 31);
		classToId.put(NetworkParticleItemSlime.class, 33);
		classToId.put(NetworkParticleHeart.class, 34);
		classToId.put(NetworkParticleBarrier.class, 35);
		classToId.put(NetworkParticleItem.class, 36);
		classToId.put(NetworkParticleBlock.class, 37);
		classToId.put(NetworkParticleRain.class, 39);
		classToId.put(NetworkParticleElderGuardian.class, 41);
		classToId.put(NetworkParticleDragonBreath.class, 42);
		classToId.put(NetworkParticleEndRod.class, 43);
		classToId.put(NetworkParticleDamageIndicator.class, 44);
		classToId.put(NetworkParticleSweepAttack.class, 45);
		classToId.put(NetworkParticleFallingDust.class, 46);
		classToId.put(NetworkParticleTotemOfUndying.class, 47);
		classToId.put(NetworkParticleSpit.class, 48);

		register(NetworkParticle.class, particle -> data_none);
		register(NetworkParticleItem.class, (version, particle) -> {
			NetworkItemStack itemstack = ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack());
			return !itemstack.isNull() ? new int[] {0, 0} : new int[] {itemstack.getTypeId(), itemstack.getLegacyData()};
		});
		register(NetworkParticleBlock.class, particle -> new int[] {
			PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(particle.getBlockData()))
		});
		register(NetworkParticleFallingDust.class, particle -> new int[] {
			PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(particle.getBlockData()))
		});
	}

	public static int getId(NetworkParticle particle) {
		int id = classToId.getInt(particle.getClass());
		if (id == -1) {
			throw new IllegalArgumentException(MessageFormat.format("No legacy id exists for particle {0}", particle.getClass()));
		}
		return id;
	}

	public static int[] getData(ProtocolVersion version, NetworkParticle particle) {
		return toData.selectPath(particle.getClass()).apply(version, particle);
	}

}