package protocolsupport.protocol.typeremapper.itemstack.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BannerMeta;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorAttributesToLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorBannerToLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorBedToLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorBookPagesToLegacyText;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorDisplayNameToLegacyText;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorEnchantToLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorItemDurabilityToLegacyData;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorLoreToLegacyText;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorMapToLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorPlayerHeadToLegacyOwner;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorPlayerHeadToLegacyUUID;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorPotionToLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorShieldToLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorSpawnEggToIntId;
import protocolsupport.protocol.typeremapper.itemstack.format.from.ItemStackLegacyFormatOperatorSpawnEggToStringId;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorAttributesFromLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorBannerFromLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorDisplayNameFromLegacyText;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorEnchantFromLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorItemDurabilityFromLegacyData;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorLoreFromLegacyText;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorMapFromLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorPotionFromLegacyId;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorShieldFromLegacy;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorSpawnEggFromIntId;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorSpawnEggFromStringId;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ItemSpawnEggData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ItemStackLegacyFormat {

	private ItemStackLegacyFormat() {
	}

	public static final ItemStackLegacyFormatRegistry REGISTRY_TO = new ItemStackLegacyFormatRegistry() {{
		add(Material.FILLED_MAP, new ItemStackLegacyFormatOperatorMapToLegacyId(), ProtocolVersionsHelper.DOWN_1_12_2);

		MinecraftItemData.getItems()
		.filter(material -> Bukkit.getItemFactory().getItemMeta(material) instanceof BannerMeta)
		.forEach(material -> {
			Integer color = LegacyBanner.getColorByMaterial(material);
			if (color != null) {
				add(material, new ItemStackLegacyFormatOperatorBannerToLegacy(color), ProtocolVersionsHelper.DOWN_1_12_2);
			}
		});

		add(Material.SHIELD, new ItemStackLegacyFormatOperatorShieldToLegacy(), ProtocolVersionsHelper.DOWN_1_12_2);

		ItemStackLegacyFormatOperatorBedToLegacy bedToLegacy = new ItemStackLegacyFormatOperatorBedToLegacy();
		add(Material.WHITE_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.ORANGE_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.MAGENTA_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.LIGHT_BLUE_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.YELLOW_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.LIME_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.PINK_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.GRAY_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.LIGHT_GRAY_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.CYAN_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.BLUE_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.PURPLE_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.GREEN_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.BROWN_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.RED_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);
		add(Material.BLACK_BED, bedToLegacy, ProtocolVersionsHelper.DOWN_1_11_1);

		add(Material.PLAYER_HEAD, new ItemStackLegacyFormatOperatorPlayerHeadToLegacyUUID(), ProtocolVersionsHelper.RANGE__1_7_10__1_15_2);
		add(Material.PLAYER_HEAD, new ItemStackLegacyFormatOperatorPlayerHeadToLegacyOwner(), ProtocolVersionsHelper.DOWN_1_7_5);

		add(Material.POTION, new ItemStackLegacyFormatOperatorPotionToLegacyId(false), ProtocolVersionsHelper.DOWN_1_8);
		add(Material.SPLASH_POTION, new ItemStackLegacyFormatOperatorPotionToLegacyId(true), ProtocolVersionsHelper.DOWN_1_8);
		add(Material.LINGERING_POTION, new ItemStackLegacyFormatOperatorPotionToLegacyId(true), ProtocolVersionsHelper.DOWN_1_8);

		add(Material.WRITTEN_BOOK, new ItemStackLegacyFormatOperatorBookPagesToLegacyText(), ProtocolVersionsHelper.DOWN_1_7_10);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			NetworkEntityType spawnedType = ItemSpawnEggData.getSpawnedType(ItemMaterialLookup.getRuntimeId(m));
			add(m, new ItemStackLegacyFormatOperatorSpawnEggToStringId(spawnedType.getKey()), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			add(m, new ItemStackLegacyFormatOperatorSpawnEggToStringId(LegacyEntityId.getStringId(spawnedType)), ProtocolVersionsHelper.RANGE__1_9__1_10);
			add(m, new ItemStackLegacyFormatOperatorSpawnEggToIntId(LegacyEntityId.getIntId(spawnedType)), ProtocolVersionsHelper.DOWN_1_8);
		});

		addAll(new ItemStackLegacyFormatOperatorAttributesToLegacy(), ProtocolVersionsHelper.DOWN_1_15_2);
		addAll(new ItemStackLegacyFormatOperatorLoreToLegacyText(), ProtocolVersionsHelper.DOWN_1_13_2);
		addAll(new ItemStackLegacyFormatOperatorEnchantToLegacyId(), ProtocolVersionsHelper.DOWN_1_12_2);
		addAll(new ItemStackLegacyFormatOperatorDisplayNameToLegacyText(), ProtocolVersionsHelper.DOWN_1_12_2);
		{
			ItemStackLegacyFormatOperatorItemDurabilityToLegacyData durabilitytolegacydata = new ItemStackLegacyFormatOperatorItemDurabilityToLegacyData();
			MinecraftItemData.getItems()
			.filter(material -> material.getMaxDurability() > 0)
			.forEach(material -> add(material, durabilitytolegacydata, ProtocolVersionsHelper.DOWN_1_12_2));
		}
	}};

	public static final ItemStackLegacyFormatRegistry REGISTRY_FROM = new ItemStackLegacyFormatRegistry() {{
		addAll(new ItemStackLegacyFormatOperatorAttributesFromLegacy(), ProtocolVersionsHelper.DOWN_1_15_2);
		addAll(new ItemStackLegacyFormatOperatorLoreFromLegacyText(), ProtocolVersionsHelper.DOWN_1_13_2);
		addAll(new ItemStackLegacyFormatOperatorEnchantFromLegacyId(), ProtocolVersionsHelper.DOWN_1_12_2);
		addAll(new ItemStackLegacyFormatOperatorDisplayNameFromLegacyText(), ProtocolVersionsHelper.DOWN_1_12_2);
		{
			ItemStackLegacyFormatOperatorItemDurabilityFromLegacyData durabilityfromlegacydata = new ItemStackLegacyFormatOperatorItemDurabilityFromLegacyData();
			MinecraftItemData.getItems()
			.forEach(material -> add(material, durabilityfromlegacydata, ProtocolVersionsHelper.DOWN_1_12_2));
		}

		add(Material.FILLED_MAP, new ItemStackLegacyFormatOperatorMapFromLegacyId(), ProtocolVersionsHelper.DOWN_1_12_2);

		MinecraftItemData.getItems()
		.filter(material -> Bukkit.getItemFactory().getItemMeta(material) instanceof BannerMeta)
		.filter(material -> LegacyBanner.getColorByMaterial(material) != null)
		.forEach(material -> add(material, new ItemStackLegacyFormatOperatorBannerFromLegacy(), ProtocolVersionsHelper.DOWN_1_12_2));

		add(Material.SHIELD, new ItemStackLegacyFormatOperatorShieldFromLegacy(), ProtocolVersionsHelper.DOWN_1_12_2);

		add(Material.POTION, new ItemStackLegacyFormatOperatorPotionFromLegacyId(), ProtocolVersionsHelper.DOWN_1_8);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(material -> {
			add(material, new ItemStackLegacyFormatOperatorSpawnEggFromStringId(false), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			add(material, new ItemStackLegacyFormatOperatorSpawnEggFromStringId(true), ProtocolVersionsHelper.RANGE__1_9__1_10);
			add(material, new ItemStackLegacyFormatOperatorSpawnEggFromIntId(), ProtocolVersionsHelper.DOWN_1_8);
		});
	}};

	public static class ItemStackLegacyFormatRegistry extends MappingRegistry<ItemStackLegacyFormatTable> {

		@Override
		protected ItemStackLegacyFormatTable createTable() {
			return new ItemStackLegacyFormatTable();
		}

		protected void set(int runtimeId, ItemStackLegacyFormatOperator operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(runtimeId, Utils.createSingletonArrayList(operator));
			}
		}

		protected void set(Material material, ItemStackLegacyFormatOperator operator, ProtocolVersion... versions) {
			set(ItemMaterialLookup.getRuntimeId(material), operator, versions);
		}

		protected void add(int runtimeId, ItemStackLegacyFormatOperator operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).add(runtimeId, operator);
			}
		}

		protected void add(Material material, ItemStackLegacyFormatOperator operator, ProtocolVersion... versions) {
			add(ItemMaterialLookup.getRuntimeId(material), operator, versions);
		}

		protected void add(Material material, ItemStackLegacyFormatOperator operator) {
			add(material, operator, ProtocolVersionsHelper.ALL);
		}

		protected void addAll(ItemStackLegacyFormatOperator operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				ItemStackLegacyFormatTable table = getTable(version);
				MinecraftItemData.getItems().forEach(material -> table.add(ItemMaterialLookup.getRuntimeId(material), operator));
			}
		}

		protected void addAll(Function<ProtocolVersion, ItemStackLegacyFormatOperator> operatorSupplier, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				ItemStackLegacyFormatTable table = getTable(version);
				ItemStackLegacyFormatOperator operator = operatorSupplier.apply(version);
				MinecraftItemData.getItems().forEach(material -> table.add(ItemMaterialLookup.getRuntimeId(material), operator));
			}
		}

		protected void addAll(Function<ProtocolVersion, ItemStackLegacyFormatOperator> operatorSupplier) {
			addAll(operatorSupplier, ProtocolVersionsHelper.ALL);
		}

	}

	public static class ItemStackLegacyFormatTable extends MappingTable {

		@SuppressWarnings("unchecked")
		protected final List<ItemStackLegacyFormatOperator>[] table = new List[MinecraftItemData.ITEM_COUNT];

		public void set(int typeId, List<ItemStackLegacyFormatOperator> list) {
			table[typeId] = new ArrayList<>(list);
		}

		public void add(int typeId, ItemStackLegacyFormatOperator operator) {
			List<ItemStackLegacyFormatOperator> list = table[typeId];
			if (list == null) {
				list = new ArrayList<>();
				table[typeId] = list;
			}
			list.add(operator);
		}

		public List<ItemStackLegacyFormatOperator> get(int typeId) {
			if ((typeId >= 0) && (typeId < table.length)) {
				List<ItemStackLegacyFormatOperator> list = table[typeId];
				if (list != null) {
					return list;
				}
			}
			return Collections.emptyList();
		}

		public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
			for (ItemStackLegacyFormatOperator operator : get(itemstack.getTypeId())) {
				itemstack = operator.apply(locale, itemstack);
			}
			return itemstack;
		}

	}

}
