package protocolsupport.protocol.typeremapper.itemstack.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Material;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.BookPagesFromPERemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.DisplayNameFromLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.EnchantFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.EnchantFromPEEnchantRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.FireworkFromPETagRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.LeatherArmorFromPERemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.LoreFromLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.MapFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromPEIdRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.NBTUnStashRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.SpawnEggFromStringIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToPESpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DisplayNameToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EmptyBookPageAdderComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantFilterNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToPEEnchantSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.FireworkToPETagSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.LeatherArmorToPESpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapItemLegacyIdToNbtSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.ItemDurabilityToLegacyDataComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.LoreToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToPEIdSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.NBTStashRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToIntIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.SpawnEggToStringIdComplexRemapper;
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
		registerToClient(Material.FILLED_MAP, new MapToLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_13);

		registerToClient(Material.DRAGON_HEAD, new DragonHeadToDragonPlayerHeadComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.PLAYER_HEAD, new PlayerHeadToLegacyOwnerComplexRemapper(), ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5));

		registerToClient(Material.POTION, new PotionToLegacyIdComplexRemapper(false), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.SPLASH_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);
		registerToClient(Material.LINGERING_POTION, new PotionToLegacyIdComplexRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);

		registerToClient(Material.WRITABLE_BOOK, new EmptyBookPageAdderComplexRemapper(), ProtocolVersionsHelper.ALL_PC);
		registerToClient(Material.WRITTEN_BOOK, new BookPagesToLegacyTextComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_8);

		ItemStackComplexRemapper durabilitymapper 	= new ItemDurabilityToLegacyDataComplexRemapper();
		ItemStackComplexRemapper enchantfilter 		= new EnchantFilterNBTComplexRemapper();
		ItemStackComplexRemapper enchanttolegacyid 	= new EnchantToLegacyIdComplexRemapper();
		ItemStackComplexRemapper dnametolegacytext 	= new DisplayNameToLegacyTextComplexRemapper();
		ItemStackComplexRemapper peenchantremapper 	= new EnchantToPEEnchantSpecificRemapper();
		ItemStackComplexRemapper pestashremapper 	= new NBTStashRemapper();
		PotionToPEIdSpecificRemapper pepotion 		= new PotionToPEIdSpecificRemapper();
		BookPagesToPESpecificRemapper pebook 		= new BookPagesToPESpecificRemapper();
		FireworkToPETagSpecificRemapper pefireworks = new FireworkToPETagSpecificRemapper();
		LeatherArmorToPESpecificRemapper peleatherarmor = new LeatherArmorToPESpecificRemapper();
		LoreToLegacyTextComplexRemapper loretolegacytext = new LoreToLegacyTextComplexRemapper();

		ItemSpawnEggData.getSpawnEggs()
		.forEach(m -> {
			NetworkEntityType spawnedType = ItemSpawnEggData.getSpawnedType(ItemMaterialLookup.getRuntimeId(m));
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(spawnedType.getKey()), ProtocolVersionsHelper.RANGE__1_11__1_12_2);
			registerToClient(m, new SpawnEggToStringIdComplexRemapper(LegacyEntityId.getStringId(spawnedType)), ProtocolVersionsHelper.RANGE__1_9__1_10);
			registerToClient(m, new SpawnEggToIntIdComplexRemapper(LegacyEntityId.getIntId(spawnedType)), ProtocolVersionsHelper.BEFORE_1_9);
		});

		MinecraftData.getItems()
		.forEach(material -> {
			registerToClient(material, pestashremapper, ProtocolVersionsHelper.ALL_PE);
			registerToClient(material, enchantfilter, ProtocolVersionsHelper.ALL_PC);
			registerToClient(material, loretolegacytext, ProtocolVersionsHelper.BEFORE_1_14);
			if (material.getMaxDurability() > 0) {
				registerToClient(material, durabilitymapper, ProtocolVersionsHelper.BEFORE_1_13_AND_PE);
			}
			registerToClient(material, enchanttolegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerToClient(material, dnametolegacytext, ProtocolVersionsHelper.BEFORE_1_13_AND_PE);
			registerToClient(material, peenchantremapper, ProtocolVersionsHelper.ALL_PE);
		});
		registerToClient(Material.FILLED_MAP, new MapItemLegacyIdToNbtSpecificRemapper(), ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.POTION, pepotion, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.SPLASH_POTION, pepotion, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.LINGERING_POTION, pepotion, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.TIPPED_ARROW, pepotion, ProtocolVersionsHelper.ALL_PE);
		//TODO FIX
		//registerToClient(Material.MONSTER_EGG, new MonsterEggToPEIdSpecificRemapper(), ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.WRITTEN_BOOK, pebook, ProtocolVersionsHelper.ALL_PE);
		//TODO FIX
		//registerToClient(Material.BOOK_AND_QUILL, pebook, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.FIREWORK_STAR, pefireworks, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.FIREWORK_ROCKET, pefireworks, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.LEATHER_HELMET, peleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.LEATHER_CHESTPLATE, peleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.LEATHER_LEGGINGS, peleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerToClient(Material.LEATHER_BOOTS, peleatherarmor, ProtocolVersionsHelper.ALL_PE);
	}

	static {
		registerFromClient(Material.FILLED_MAP, new MapFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_13);
		registerFromClient(Material.POTION, new PotionFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		ItemStackComplexRemapper enchantfromlegacyid 	= new EnchantFromLegacyIdComplexRemapper();
		LoreFromLegacyTextComplexRemapper lorefromlegacytext = new LoreFromLegacyTextComplexRemapper();
		ItemStackComplexRemapper dnamefromlegacytext 	= new DisplayNameFromLegacyTextComplexRemapper();
		ItemStackComplexRemapper frompeenchantremapper 	= new EnchantFromPEEnchantRemapper();
		ItemStackComplexRemapper pestashremapper 		= new NBTUnStashRemapper();
		BookPagesFromPERemapper frompebook 				= new BookPagesFromPERemapper();
		PotionFromPEIdRemapper frompepotion 			= new PotionFromPEIdRemapper();
		LeatherArmorFromPERemapper frompeleatherarmor 	= new LeatherArmorFromPERemapper();
		FireworkFromPETagRemapper frompefireworks       = new FireworkFromPETagRemapper();

		MinecraftData.getItems()
		.forEach(material -> {
			//TODO: do we need these anymore? backwards mapping done with stashed NBT now
			registerFromClient(material, dnamefromlegacytext, ProtocolVersionsHelper.BEFORE_1_13);
			registerFromClient(material, lorefromlegacytext, ProtocolVersionsHelper.BEFORE_1_14);
			registerFromClient(material, enchantfromlegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerFromClient(material, frompeenchantremapper, ProtocolVersionsHelper.ALL_PE);
		});
		//TODO FIX
		//registerFromClient(Material.MAP, new MapItemNbtToLegacyIdRemapper(), ProtocolVersionsHelper.ALL_PE);
		//registerFromClient(Material.MONSTER_EGG, new MonsterEggFromPEIdRemapper(), ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.WRITTEN_BOOK, frompebook, ProtocolVersionsHelper.ALL_PE);
		//TODO FIX
		//registerFromClient(Material.BOOK_AND_QUILL, frompebook, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.POTION, frompepotion, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.SPLASH_POTION, frompepotion, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.LINGERING_POTION, frompepotion, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.TIPPED_ARROW, frompepotion, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.LEATHER_HELMET, frompeleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.LEATHER_CHESTPLATE, frompeleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.LEATHER_LEGGINGS, frompeleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.LEATHER_BOOTS, frompeleatherarmor, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.FIREWORK_STAR, frompefireworks, ProtocolVersionsHelper.ALL_PE);
		registerFromClient(Material.FIREWORK_ROCKET, frompefireworks, ProtocolVersionsHelper.ALL_PE);
		Arrays.stream(Material.values())
		.filter(Material::isItem)
		.forEach(material -> {
			registerFromClient(material, pestashremapper, ProtocolVersionsHelper.ALL_PE);
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
