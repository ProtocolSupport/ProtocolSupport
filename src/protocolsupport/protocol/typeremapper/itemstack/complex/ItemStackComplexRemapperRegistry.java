package protocolsupport.protocol.typeremapper.itemstack.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Material;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.DisplayNameFromLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.EnchantFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.MapFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromStringIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DisplayNameToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EmptyBookPageAdderComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantFilterNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.ItemDurabilityToLegacyDataComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToStringIdComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ItemSpawnEggData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkItemStack;
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
		registerToClient(Material.FILLED_MAP, new MapToLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_13);

		registerToClient(Material.DRAGON_HEAD, new DragonHeadToDragonPlayerHeadComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.PLAYER_HEAD, new PlayerHeadToLegacyOwnerComplexRemapper(), ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5));

		registerToClient(Material.POTION, new PotionToLegacyIdComplexRemapper(false), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.SPLASH_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.LINGERING_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);

		registerToClient(Material.WRITABLE_BOOK, new EmptyBookPageAdderComplexRemapper(), ProtocolVersionsHelper.ALL_PC);
		registerToClient(Material.WRITTEN_BOOK, new BookPagesToLegacyTextComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_8);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			NetworkEntityType spawnedType = ItemSpawnEggData.getSpawnedType(ItemMaterialLookup.getRuntimeId(m));
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(spawnedType.getKey()), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(LegacyEntityId.getStringId(spawnedType)), ProtocolVersionsHelper.RANGE__1_9__1_10);
			registerToClient(m, new SpawnEggToIntIdComplexRemapper(LegacyEntityId.getIntId(spawnedType)), ProtocolVersionsHelper.BEFORE_1_9);
		});

		ItemDurabilityToLegacyDataComplexRemapper durabilitymapper = new ItemDurabilityToLegacyDataComplexRemapper();
		EnchantFilterNBTComplexRemapper enchantfilter = new EnchantFilterNBTComplexRemapper();
		EnchantToLegacyIdComplexRemapper enchanttolegacyid = new EnchantToLegacyIdComplexRemapper();
		DisplayNameToLegacyTextComplexRemapper dnametolegacytext = new DisplayNameToLegacyTextComplexRemapper();
		MinecraftData.getItems()
		.forEach(material -> {
			if (material.getMaxDurability() > 0) {
				registerToClient(material, durabilitymapper, ProtocolVersionsHelper.BEFORE_1_13);
			}
			registerToClient(material, enchantfilter, ProtocolVersionsHelper.ALL_PC);
			registerToClient(material, enchanttolegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerToClient(material, dnametolegacytext, ProtocolVersionsHelper.BEFORE_1_13);
		});
	}

	static {
		EnchantFromLegacyIdComplexRemapper enchantfromlegacyid = new EnchantFromLegacyIdComplexRemapper();
		DisplayNameFromLegacyTextComplexRemapper dnamefromlegacytext = new DisplayNameFromLegacyTextComplexRemapper();
		Arrays.stream(Material.values())
		.filter(Material::isItem)
		.forEach(material -> {
			registerFromClient(material, enchantfromlegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerFromClient(material, dnamefromlegacytext, ProtocolVersionsHelper.BEFORE_1_13);
		});

		registerFromClient(Material.FILLED_MAP, new MapFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_13);

		registerFromClient(Material.POTION, new PotionFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			registerFromClient(m, new SpawnEggFromStringIdComplexRemapper(false), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			registerFromClient(m, new SpawnEggFromStringIdComplexRemapper(true), ProtocolVersionsHelper.RANGE__1_9__1_10);
			registerFromClient(m, new SpawnEggFromIntIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
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
