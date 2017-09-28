package protocolsupport.protocol.typeremapper.id;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.EnumSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.GenericSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.IntSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.HashMapBasedIntSkippingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Particle;
import protocolsupport.protocol.utils.types.WindowType;

public class IdSkipper {

	public static final EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>> ENTITY = new EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>>() {
		{
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_MOB, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_OBJECT, ProtocolVersionsHelper.BEFORE_1_8);
		}

		@Override
		protected EnumSkippingTable<NetworkEntityType> createTable() {
			return new EnumSkippingTable<>(NetworkEntityType.class);
		}

	};

	public static final IntSkippingRegistry<HashMapBasedIntSkippingTable> ENCHANT = new IntSkippingRegistry<HashMapBasedIntSkippingTable>() {
		{
			registerSkipEntry(Enchantment.SWEEPING_EDGE, ProtocolVersionsHelper.BEFORE_1_11_1);
			registerSkipEntry(Enchantment.BINDING_CURSE, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Enchantment.VANISHING_CURSE, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Enchantment.FROST_WALKER, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Enchantment.MENDING, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Enchantment.DEPTH_STRIDER, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(Enchantment.LURE, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.LUCK, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.BINDING_CURSE, ProtocolVersion.MINECRAFT_PE);
			registerSkipEntry(Enchantment.SWEEPING_EDGE, ProtocolVersion.MINECRAFT_PE);
			registerSkipEntry(Enchantment.VANISHING_CURSE, ProtocolVersion.MINECRAFT_PE);
		}
		@SuppressWarnings("deprecation")
		protected void registerSkipEntry(Enchantment ench, ProtocolVersion... versions) {
			registerSkipEntry(ench.getId(), versions);
		}
		@Override
		protected HashMapBasedIntSkippingTable createTable() {
			return new HashMapBasedIntSkippingTable();
		}
	};

	public static final IntSkippingRegistry<ArrayBasedIntSkippingTable> EFFECT = new IntSkippingRegistry<ArrayBasedIntSkippingTable>() {
		{
			registerSkipEntry(PotionEffectType.GLOWING, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(PotionEffectType.LEVITATION, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(PotionEffectType.LUCK, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(PotionEffectType.UNLUCK, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(PotionEffectType.HEALTH_BOOST, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(PotionEffectType.ABSORPTION, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(PotionEffectType.SATURATION, ProtocolVersionsHelper.BEFORE_1_6);
		}
		@SuppressWarnings("deprecation")
		protected void registerSkipEntry(PotionEffectType effecttype, ProtocolVersion... versions) {
			registerSkipEntry(effecttype.getId(), versions);
		}
		@Override
		protected ArrayBasedIntSkippingTable createTable() {
			return new ArrayBasedIntSkippingTable(32);
		}
	};

	public static final GenericSkippingRegistry<String, GenericSkippingTable<String>> ATTRIBUTES = new GenericSkippingRegistry<String, GenericSkippingTable<String>>() {
		{
			registerSkipEntry("generic.flyingSpeed", ProtocolVersionsHelper.BEFORE_1_12);
			registerSkipEntry("generic.armorToughness", ProtocolVersionsHelper.BEFORE_1_9_1);
			registerSkipEntry("generic.luck", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.armor", ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry("generic.attackSpeed", ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected GenericSkippingTable<String> createTable() {
			return new GenericSkippingTable<>();
		}
	};

	public static final EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>> INVENTORY = new EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>>() {
		{
			registerSkipEntry(WindowType.HORSE, ProtocolVersionsHelper.BEFORE_1_6);
			registerSkipEntry(WindowType.HOPPER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected EnumSkippingTable<WindowType> createTable() {
			return new EnumSkippingTable<>(WindowType.class);
		}
	};

	public static final EnumSkippingRegistry<Particle, EnumSkippingTable<Particle>> PARTICLE = new EnumSkippingRegistry<Particle, SkippingTable.EnumSkippingTable<Particle>>() {
		{
			registerSkipEntry(Particle.TOTEM, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkipEntry(Particle.SPIT, ProtocolVersionsHelper.BEFORE_1_11);
			registerSkipEntry(Particle.FALLING_DUST, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Particle.DRAGON_BREATH, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.END_ROD, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.DAMAGE_INDICATOR, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Particle.SWEEP_ATTACK, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected EnumSkippingTable<Particle> createTable() {
			return new EnumSkippingTable<>(Particle.class);
		}
	};

}
