package protocolsupport.protocol.typeremapper.particle;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.types.NetworkParticleAmbientEntityEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleAngryVillager;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlock;
import protocolsupport.protocol.types.particle.types.NetworkParticleBubble;
import protocolsupport.protocol.types.particle.types.NetworkParticleCloud;
import protocolsupport.protocol.types.particle.types.NetworkParticleCrit;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingLava;
import protocolsupport.protocol.types.particle.types.NetworkParticleDrippingWater;
import protocolsupport.protocol.types.particle.types.NetworkParticleDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleEffect;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchant;
import protocolsupport.protocol.types.particle.types.NetworkParticleEnchantedHit;
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
import protocolsupport.protocol.types.particle.types.NetworkParticleSmoke;
import protocolsupport.protocol.types.particle.types.NetworkParticleSplash;
import protocolsupport.protocol.types.particle.types.NetworkParticleUnderwater;
import protocolsupport.protocol.types.particle.types.NetworkParticleWitch;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PreFlatteningNetworkParticleStringIdRegistryDataSerializer {

	private PreFlatteningNetworkParticleStringIdRegistryDataSerializer() {
	}

	private static final Map<Class<? extends NetworkParticle>, String> classToId = new HashMap<>();
	private static final CachedInstanceOfChain<BiFunction<ProtocolVersion, NetworkParticle, String>> toData = new CachedInstanceOfChain<>();

	private static <L extends NetworkParticle> void register(Class<L> clazz, Function<L, String> function) {
		register(clazz, (protocol, particle) -> function.apply(particle));
	}

	@SuppressWarnings("unchecked")
	private static <L extends NetworkParticle> void register(Class<L> clazz, BiFunction<ProtocolVersion, L, String> function) {
		toData.setKnownPath(clazz, (BiFunction<ProtocolVersion, NetworkParticle, String>) function);
	}

	static {
		classToId.put(NetworkParticlePoof.class, "explode");
		classToId.put(NetworkParticleExplosion.class, "largeexplode");
		classToId.put(NetworkParticleExplosionEmitter.class, "hugeexplosion");
		classToId.put(NetworkParticleFirework.class, "fireworksSpark");
		classToId.put(NetworkParticleBubble.class, "bubble");
		classToId.put(NetworkParticleSplash.class, "splash");
		classToId.put(NetworkParticleFishing.class, "wake");
		classToId.put(NetworkParticleUnderwater.class, "suspended");
		classToId.put(NetworkParticleCrit.class, "crit");
		classToId.put(NetworkParticleEnchantedHit.class, "magicCrit");
		classToId.put(NetworkParticleSmoke.class, "smoke");
		classToId.put(NetworkParticleLargeSmoke.class, "largesmoke");
		classToId.put(NetworkParticleEffect.class, "spell");
		classToId.put(NetworkParticleInstantEffect.class, "instantSpell");
		classToId.put(NetworkParticleEntityEffect.class, "mobSpell");
		classToId.put(NetworkParticleAmbientEntityEffect.class, "mobSpellAmbient");
		classToId.put(NetworkParticleWitch.class, "witchMagic");
		classToId.put(NetworkParticleDrippingWater.class, "dripWater");
		classToId.put(NetworkParticleDrippingLava.class, "dripLava");
		classToId.put(NetworkParticleAngryVillager.class, "angryVillager");
		classToId.put(NetworkParticleHappyVillager.class, "happyVillager");
		classToId.put(NetworkParticleMycelium.class, "townaura");
		classToId.put(NetworkParticleNote.class, "note");
		classToId.put(NetworkParticlePortal.class, "portal");
		classToId.put(NetworkParticleEnchant.class, "enchantmenttable");
		classToId.put(NetworkParticleFlame.class, "flame");
		classToId.put(NetworkParticleLava.class, "lava");
		classToId.put(NetworkParticleCloud.class, "cloud");
		classToId.put(NetworkParticleDust.class, "reddust");
		classToId.put(NetworkParticleItemSnowball.class, "snowballpoof");
		classToId.put(NetworkParticleItemSlime.class, "slime");
		classToId.put(NetworkParticleHeart.class, "heart");
		classToId.put(NetworkParticleItem.class, "iconcrack");
		classToId.put(NetworkParticleBlock.class, "blockcrack");
		classToId.put(NetworkParticleFallingDust.class, "blockdust");

		register(NetworkParticle.class, particle -> "");
		register(NetworkParticleItem.class, (version, particle) -> {
			NetworkItemStack itemstack = ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack());
			return !itemstack.isNull() ? ("_" + itemstack.getTypeId() + "_" + itemstack.getLegacyData()) : ("_0_0");
		});
		register(NetworkParticleBlock.class, particle -> {
			int lBlockData = PreFlatteningBlockIdData.getCombinedId(particle.getBlockData());
			return "_" + PreFlatteningBlockIdData.getIdFromCombinedId(lBlockData) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(lBlockData);
		});
		register(NetworkParticleFallingDust.class, particle -> {
			int lBlockData = PreFlatteningBlockIdData.getCombinedId(particle.getBlockData());
			return "_" + PreFlatteningBlockIdData.getIdFromCombinedId(lBlockData) + "_" + PreFlatteningBlockIdData.getDataFromCombinedId(lBlockData);
		});
	}

	public static String getId(NetworkParticle particle) {
		String id = classToId.get(particle.getClass());
		if (id == null) {
			throw new IllegalArgumentException(MessageFormat.format("No legacy id exists for particle {0}", particle.getClass()));
		}
		return id;
	}

	public static String getData(ProtocolVersion version, NetworkParticle particle) {
		return toData.selectPath(particle.getClass()).apply(version, particle);
	}

	public static String getIdData(ProtocolVersion version, NetworkParticle particle) {
		return getId(particle) + getData(version, particle);
	}

}