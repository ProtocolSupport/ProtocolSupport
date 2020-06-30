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
import protocolsupport.protocol.utils.minecraftdata.MinecraftPotionData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class GenericIdSkipper {

	public static final EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>> ENTITY = new EnumSkippingRegistry<NetworkEntityType, EnumSkippingTable<NetworkEntityType>>() {
		{
			registerSkipEntry(NetworkEntityType.AREA_EFFECT_CLOUD, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_MOB, ProtocolVersionsHelper.DOWN_1_7_10);
			registerSkipEntry(NetworkEntityType.ARMOR_STAND_OBJECT, ProtocolVersionsHelper.DOWN_1_7_10);
			registerSkipEntry(NetworkEntityType.LEASH_KNOT, ProtocolVersionsHelper.DOWN_1_5_2);
		}

		@Override
		protected EnumSkippingTable<NetworkEntityType> createTable() {
			return new EnumSkippingTable<>(NetworkEntityType.class);
		}

	};

	public static final GenericSkippingRegistry<String, GenericSkippingTable<String>> ENCHANT = new GenericSkippingRegistry<String, GenericSkippingTable<String>>() {
		{
			registerSkipEntry(Enchantment.CHANNELING, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(Enchantment.IMPALING, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(Enchantment.LOYALTY, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(Enchantment.RIPTIDE, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(Enchantment.SWEEPING_EDGE, ProtocolVersionsHelper.DOWN_1_11);
			registerSkipEntry(Enchantment.BINDING_CURSE, ProtocolVersionsHelper.DOWN_1_9_4);
			registerSkipEntry(Enchantment.VANISHING_CURSE, ProtocolVersionsHelper.DOWN_1_9_4);
			registerSkipEntry(Enchantment.FROST_WALKER, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(Enchantment.MENDING, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(Enchantment.DEPTH_STRIDER, ProtocolVersionsHelper.DOWN_1_7_10);
			registerSkipEntry(Enchantment.LURE, ProtocolVersionsHelper.DOWN_1_6_4);
			registerSkipEntry(Enchantment.LUCK, ProtocolVersionsHelper.DOWN_1_6_4);
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
			registerSkipEntry(PotionEffectType.HERO_OF_THE_VILLAGE, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(PotionEffectType.BAD_OMEN, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(PotionEffectType.CONDUIT_POWER, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(PotionEffectType.SLOW_FALLING, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(PotionEffectType.DOLPHINS_GRACE, ProtocolVersionsHelper.DOWN_1_12_2);
			registerSkipEntry(PotionEffectType.GLOWING, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(PotionEffectType.LEVITATION, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(PotionEffectType.LUCK, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(PotionEffectType.UNLUCK, ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry(PotionEffectType.HEALTH_BOOST, ProtocolVersionsHelper.DOWN_1_5_2);
			registerSkipEntry(PotionEffectType.ABSORPTION, ProtocolVersionsHelper.DOWN_1_5_2);
			registerSkipEntry(PotionEffectType.SATURATION, ProtocolVersionsHelper.DOWN_1_5_2);
		}
		@SuppressWarnings("deprecation")
		protected void registerSkipEntry(PotionEffectType effecttype, ProtocolVersion... versions) {
			registerSkipEntry(effecttype.getId(), versions);
		}
		@Override
		protected ArrayBasedIntSkippingTable createTable() {
			return new ArrayBasedIntSkippingTable(MinecraftPotionData.ID_MAX + 1);
		}
	};

	public static final GenericSkippingRegistry<String, GenericSkippingTable<String>> ATTRIBUTES = new GenericSkippingRegistry<String, GenericSkippingTable<String>>() {
		{
			registerSkipEntry("generic.flying_speed", ProtocolVersionsHelper.DOWN_1_11_1);
			registerSkipEntry("generic.armor_toughness", ProtocolVersionsHelper.DOWN_1_9);
			registerSkipEntry("generic.luck", ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry("generic.armor", ProtocolVersionsHelper.DOWN_1_8);
			registerSkipEntry("generic.attack_speed", ProtocolVersionsHelper.DOWN_1_8);
		}
		@Override
		protected GenericSkippingTable<String> createTable() {
			return new GenericSkippingTable<>();
		}
	};

	public static final EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>> INVENTORY = new EnumSkippingRegistry<WindowType, EnumSkippingTable<WindowType>>() {
		{
			registerSkipEntry(WindowType.STONECUTTER, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.LECTERN, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.CARTOGRAPHY, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.STONECUTTER, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.GRINDSTONE, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.LOOM, ProtocolVersionsHelper.DOWN_1_13_2);
			registerSkipEntry(WindowType.HOPPER, ProtocolVersionsHelper.DOWN_1_4_7);
		}
		@Override
		protected EnumSkippingTable<WindowType> createTable() {
			return new EnumSkippingTable<>(WindowType.class);
		}
	};

}
