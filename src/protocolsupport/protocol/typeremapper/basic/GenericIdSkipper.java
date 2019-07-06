package protocolsupport.protocol.typeremapper.basic;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.EnumSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.GenericSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingRegistry.IntSkippingRegistry;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.EnumSkippingTable;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class GenericIdSkipper {

	public static final EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>> ENTITY = new EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>>() {
		{
			registerSkipEntry(NetworkEntityType.AREA_EFFECT_CLOUD, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_MOB, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_OBJECT, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(NetworkEntityType.LEASH_KNOT, ProtocolVersionsHelper.BEFORE_1_6);
		}

		@Override
		protected EnumSkippingTable<NetworkEntityType> createTable() {
			return new EnumSkippingTable<>(NetworkEntityType.class);
		}

	};

	public static final GenericSkippingRegistry<String, GenericSkippingTable<String>> ENCHANT = new GenericSkippingRegistry<String, GenericSkippingTable<String>>() {
		{
			registerSkipEntry(Enchantment.CHANNELING, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(Enchantment.IMPALING, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(Enchantment.LOYALTY, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(Enchantment.RIPTIDE, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(Enchantment.SWEEPING_EDGE, ProtocolVersionsHelper.BEFORE_1_11_1);
			registerSkipEntry(Enchantment.BINDING_CURSE, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Enchantment.VANISHING_CURSE, ProtocolVersionsHelper.BEFORE_1_10);
			registerSkipEntry(Enchantment.FROST_WALKER, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Enchantment.MENDING, ProtocolVersionsHelper.BEFORE_1_9);
			registerSkipEntry(Enchantment.DEPTH_STRIDER, ProtocolVersionsHelper.BEFORE_1_8);
			registerSkipEntry(Enchantment.LURE, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.LUCK, ProtocolVersionsHelper.BEFORE_1_7);
			registerSkipEntry(Enchantment.BINDING_CURSE, ProtocolVersionsHelper.ALL_PE);
			registerSkipEntry(Enchantment.SWEEPING_EDGE, ProtocolVersionsHelper.ALL_PE);
			registerSkipEntry(Enchantment.VANISHING_CURSE, ProtocolVersionsHelper.ALL_PE);
		}
		protected void registerSkipEntry(Enchantment ench, ProtocolVersion... versions) {
			registerSkipEntry(ench.getKey().getKey(), versions);
			registerSkipEntry(ench.getKey().toString(), versions);
		}
		@Override
		protected GenericSkippingTable<String> createTable() {
			return new GenericSkippingTable<>();
		}
	};

	public static final IntSkippingRegistry<ArrayBasedIntSkippingTable> EFFECT = new IntSkippingRegistry<ArrayBasedIntSkippingTable>() {
		{
			registerSkipEntry(PotionEffectType.HERO_OF_THE_VILLAGE, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(PotionEffectType.BAD_OMEN, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(PotionEffectType.CONDUIT_POWER, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(PotionEffectType.SLOW_FALLING, ProtocolVersionsHelper.BEFORE_1_13);
			registerSkipEntry(PotionEffectType.DOLPHINS_GRACE, ProtocolVersionsHelper.BEFORE_1_13);
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
			return new ArrayBasedIntSkippingTable(33);
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
			registerSkipEntry(WindowType.STONECUTTER, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.LECTERN, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.CARTOGRAPHY, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.STONECUTTER, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.GRINDSTONE, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.LOOM, ProtocolVersionsHelper.BEFORE_1_14);
			registerSkipEntry(WindowType.HOPPER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected EnumSkippingTable<WindowType> createTable() {
			return new EnumSkippingTable<>(WindowType.class);
		}
	};

}
