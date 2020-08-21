package protocolsupport.protocol.typeremapper.itemstack.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BannerMeta;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.AttributesFromLegacyComplexNBTRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.BannerFromLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.DisplayNameFromLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.EnchantFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.ItemDurabilityFromLegacyDataComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.LoreFromLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.MapFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.ShieldFromLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromStringIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.AttributesToLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BannerToLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DisplayNameToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EmptyBookPageAdderComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantFilterNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.ItemDurabilityToLegacyDataComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.LoreToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyUUIDComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.ShieldToLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToStringIdComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ItemSpawnEggData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ItemStackComplexRemapperRegistry {

	protected static final Int2ObjectOpenHashMap<EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>>> toClientRemapper = new Int2ObjectOpenHashMap<>();
	protected static final Int2ObjectOpenHashMap<EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>>> fromClientRemapper = new Int2ObjectOpenHashMap<>();

	protected static void registerToClient(Material material, ItemStackComplexRemapper transformer, ProtocolVersion... versions) {
		register(toClientRemapper, material, transformer, versions);
	}

	protected static void registerFromClient(Material material, ItemStackComplexRemapper transformer, ProtocolVersion... versions) {
		register(fromClientRemapper, material, transformer, versions);
	}

	protected static void register(
		Int2ObjectOpenHashMap<EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>>> registry,
		Material material, ItemStackComplexRemapper transformer, ProtocolVersion... versions
	) {
		if (!material.isItem()) {
			throw new IllegalArgumentException(material + " is not an item");
		}
		EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>> map = registry.computeIfAbsent(ItemMaterialLookup.getRuntimeId(material), k -> new EnumMap<>(ProtocolVersion.class));
		Arrays.stream(versions).forEach(version -> map.computeIfAbsent(version, k -> new ArrayList<>()).add(transformer));
	}

	static {
		registerToClient(Material.FILLED_MAP, new MapToLegacyIdComplexRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);
		MinecraftData.getItems()
		.filter(material -> Bukkit.getItemFactory().getItemMeta(material) instanceof BannerMeta)
		.forEach(material -> {
			Integer color = LegacyBanner.getColorByMaterial(material);
			if (color != null) {
				registerToClient(material, new BannerToLegacyComplexRemapper(color), ProtocolVersionsHelper.DOWN_1_12_2);
			}
		});
		registerToClient(Material.SHIELD, new ShieldToLegacyComplexRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);

		{
			DragonHeadToDragonPlayerHeadComplexRemapper dragonHeadToPlayerHead = new DragonHeadToDragonPlayerHeadComplexRemapper();
			PlayerHeadToLegacyUUIDComplexRemapper playerHeadToLegacyUUID = new PlayerHeadToLegacyUUIDComplexRemapper();
			PlayerHeadToLegacyOwnerComplexRemapper playerHeadToLegacyOwner = new PlayerHeadToLegacyOwnerComplexRemapper();

			registerToClient(Material.DRAGON_HEAD, dragonHeadToPlayerHead, ProtocolVersionsHelper.DOWN_1_8);
			registerToClient(Material.DRAGON_HEAD, playerHeadToLegacyUUID, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_8));
			registerToClient(Material.DRAGON_HEAD, playerHeadToLegacyOwner, ProtocolVersionsHelper.DOWN_1_7_5);

			registerToClient(Material.PLAYER_HEAD, playerHeadToLegacyUUID, ProtocolVersionsHelper.RANGE__1_7_10__1_15_2);
			registerToClient(Material.PLAYER_HEAD, playerHeadToLegacyOwner, ProtocolVersionsHelper.DOWN_1_7_5);
		}

		registerToClient(Material.POTION, new PotionToLegacyIdComplexRemapper(false), ProtocolVersionsHelper.DOWN_1_8);
		registerToClient(Material.SPLASH_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.DOWN_1_8);
		registerToClient(Material.LINGERING_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.DOWN_1_8);

		registerToClient(Material.WRITABLE_BOOK, new EmptyBookPageAdderComplexRemapper(), ProtocolVersionsHelper.ALL_PC);
		registerToClient(Material.WRITTEN_BOOK, new BookPagesToLegacyTextComplexRemapper(), ProtocolVersionsHelper.DOWN_1_7_10);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			NetworkEntityType spawnedType = ItemSpawnEggData.getSpawnedType(ItemMaterialLookup.getRuntimeId(m));
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(spawnedType.getKey()), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(LegacyEntityId.getStringId(spawnedType)), ProtocolVersionsHelper.RANGE__1_9__1_10);
			registerToClient(m, new SpawnEggToIntIdComplexRemapper(LegacyEntityId.getIntId(spawnedType)), ProtocolVersionsHelper.DOWN_1_8);
		});

		AttributesToLegacyComplexRemapper attributesToLegacyId = new AttributesToLegacyComplexRemapper();
		EnchantFilterNBTComplexRemapper enchantfilter = new EnchantFilterNBTComplexRemapper();
		LoreToLegacyTextComplexRemapper loretolegacytext = new LoreToLegacyTextComplexRemapper();
		ItemDurabilityToLegacyDataComplexRemapper durabilitytolegacydata = new ItemDurabilityToLegacyDataComplexRemapper();
		EnchantToLegacyIdComplexRemapper enchanttolegacyid = new EnchantToLegacyIdComplexRemapper();
		DisplayNameToLegacyTextComplexRemapper dnametolegacytext = new DisplayNameToLegacyTextComplexRemapper();
		MinecraftData.getItems()
		.forEach(material -> {
			registerToClient(material, enchantfilter, ProtocolVersionsHelper.ALL_PC);
			registerToClient(material, attributesToLegacyId, ProtocolVersionsHelper.DOWN_1_15_2);
			registerToClient(material, loretolegacytext, ProtocolVersionsHelper.DOWN_1_13_2);
			if (material.getMaxDurability() > 0) {
				registerToClient(material, durabilitytolegacydata, ProtocolVersionsHelper.DOWN_1_12_2);
			}
			registerToClient(material, enchanttolegacyid, ProtocolVersionsHelper.DOWN_1_12_2);
			registerToClient(material, dnametolegacytext, ProtocolVersionsHelper.DOWN_1_12_2);
		});
	}

	static {
		AttributesFromLegacyComplexNBTRemapper attributesFromLegacyId = new AttributesFromLegacyComplexNBTRemapper();
		LoreFromLegacyTextComplexRemapper lorefromlegacytext = new LoreFromLegacyTextComplexRemapper();
		ItemDurabilityFromLegacyDataComplexRemapper durabilityfromlegacydata = new ItemDurabilityFromLegacyDataComplexRemapper();
		EnchantFromLegacyIdComplexRemapper enchantfromlegacyid = new EnchantFromLegacyIdComplexRemapper();
		DisplayNameFromLegacyTextComplexRemapper dnamefromlegacytext = new DisplayNameFromLegacyTextComplexRemapper();
		MinecraftData.getItems()
		.forEach(material -> {
			registerFromClient(material, attributesFromLegacyId, ProtocolVersionsHelper.DOWN_1_15_2);
			registerFromClient(material, lorefromlegacytext, ProtocolVersionsHelper.DOWN_1_13_2);
			if (material.getMaxDurability() > 0) {
				registerToClient(material, durabilityfromlegacydata, ProtocolVersionsHelper.DOWN_1_12_2);
			}
			registerFromClient(material, enchantfromlegacyid, ProtocolVersionsHelper.DOWN_1_12_2);
			registerFromClient(material, dnamefromlegacytext, ProtocolVersionsHelper.DOWN_1_12_2);
		});

		registerFromClient(Material.FILLED_MAP, new MapFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);
		MinecraftData.getItems()
		.filter(material -> Bukkit.getItemFactory().getItemMeta(material) instanceof BannerMeta)
		.filter(material -> LegacyBanner.getColorByMaterial(material) != null)
		.forEach(material -> registerFromClient(
			material, new BannerFromLegacyComplexRemapper(), ProtocolVersionsHelper.DOWN_1_12_2
		));
		registerFromClient(Material.SHIELD, new ShieldFromLegacyComplexRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);

		registerFromClient(Material.POTION, new PotionFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.DOWN_1_8);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			registerFromClient(m, new SpawnEggFromStringIdComplexRemapper(false), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			registerFromClient(m, new SpawnEggFromStringIdComplexRemapper(true), ProtocolVersionsHelper.RANGE__1_9__1_10);
			registerFromClient(m, new SpawnEggFromIntIdComplexRemapper(), ProtocolVersionsHelper.DOWN_1_8);
		});
	}

	protected static NetworkItemStack remapComplex(
		Int2ObjectOpenHashMap<EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>>> registry,
		ProtocolVersion version, String locale,
		NetworkItemStack itemstack
	) {
		EnumMap<ProtocolVersion, List<ItemStackComplexRemapper>> map = registry.get(itemstack.getTypeId());
		if (map != null) {
			List<ItemStackComplexRemapper> transformers = map.get(version);
			if (transformers != null) {
				for (ItemStackComplexRemapper transformer : transformers) {
					itemstack = transformer.remap(version, locale, itemstack);
				}
			}
		}
		return itemstack;
	}

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		return remapComplex(toClientRemapper, version, locale, itemstack);
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		return remapComplex(fromClientRemapper, version, locale, itemstack);
	}

}
