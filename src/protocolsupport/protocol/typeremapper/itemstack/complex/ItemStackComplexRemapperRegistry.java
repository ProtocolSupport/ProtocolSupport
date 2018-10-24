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
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.LeatherArmorFromPERemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.MapFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.fromclient.PotionFromPEIdRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.BookPagesToPESpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DisplayNameToLegacyTextComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EmptyBookPageAdderComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantFilterNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.EnchantToPEEnchantSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.LeatherArmorToPESpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapItemLegacyIdToNbtSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.ItemDurabilityToLegacyDataComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.MapToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToLegacyIdComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToPEIdSpecificRemapper;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.NetworkItemStack;

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
		ItemDurabilityToLegacyDataComplexRemapper durabilitymapper = new ItemDurabilityToLegacyDataComplexRemapper();
		EnchantFilterNBTComplexRemapper enchantfilter = new EnchantFilterNBTComplexRemapper();
		EnchantToLegacyIdComplexRemapper enchanttolegacyid = new EnchantToLegacyIdComplexRemapper();
		DisplayNameToLegacyTextComplexRemapper dnametolegacytext = new DisplayNameToLegacyTextComplexRemapper();
		EnchantToPEEnchantSpecificRemapper peenchantremapper = new EnchantToPEEnchantSpecificRemapper();
		Arrays.stream(Material.values())
		.filter(Material::isItem)
		.forEach(material -> {
			if (material.getMaxDurability() > 0) {
				registerToClient(material, durabilitymapper, ProtocolVersionsHelper.BEFORE_1_13);
			}
			registerToClient(material, enchantfilter, ProtocolVersionsHelper.ALL_PC);
			registerToClient(material, enchanttolegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerToClient(material, dnametolegacytext, ProtocolVersionsHelper.BEFORE_1_13);
			registerToClient(material, peenchantremapper, ProtocolVersion.MINECRAFT_PE);
		});
		registerToClient(Material.MAP, new MapItemLegacyIdToNbtSpecificRemapper(), ProtocolVersion.MINECRAFT_PE);
		PotionToPEIdSpecificRemapper pepotion = new PotionToPEIdSpecificRemapper();
		registerToClient(Material.POTION, pepotion, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.SPLASH_POTION, pepotion, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.LINGERING_POTION, pepotion, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.TIPPED_ARROW, pepotion, ProtocolVersion.MINECRAFT_PE);
		//TODO FIX
		//registerToClient(Material.MONSTER_EGG, new MonsterEggToPEIdSpecificRemapper(), ProtocolVersion.MINECRAFT_PE);
		BookPagesToPESpecificRemapper pebook = new BookPagesToPESpecificRemapper();
		registerToClient(Material.WRITTEN_BOOK, pebook, ProtocolVersion.MINECRAFT_PE);
		//TODO FIX
		//registerToClient(Material.BOOK_AND_QUILL, pebook, ProtocolVersion.MINECRAFT_PE);
		//FireworkToPETagSpecificRemapper pefireworks = new FireworkToPETagSpecificRemapper();
		//TODO FIX
		//registerToClient(Material.FIREWORK_CHARGE, pefireworks, ProtocolVersion.MINECRAFT_PE);
		//registerToClient(Material.FIREWORK, pefireworks, ProtocolVersion.MINECRAFT_PE);
		LeatherArmorToPESpecificRemapper peleatherarmor = new LeatherArmorToPESpecificRemapper();
		registerToClient(Material.LEATHER_HELMET, peleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.LEATHER_CHESTPLATE, peleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.LEATHER_LEGGINGS, peleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerToClient(Material.LEATHER_BOOTS, peleatherarmor, ProtocolVersion.MINECRAFT_PE);
	}

	static {
		registerFromClient(Material.FILLED_MAP, new MapFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_13);
		registerFromClient(Material.POTION, new PotionFromLegacyIdComplexRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		EnchantFromLegacyIdComplexRemapper enchantfromlegacyid = new EnchantFromLegacyIdComplexRemapper();
		DisplayNameFromLegacyTextComplexRemapper dnamefromlegacytext = new DisplayNameFromLegacyTextComplexRemapper();
		EnchantFromPEEnchantRemapper frompeenchantremapper = new EnchantFromPEEnchantRemapper();
		Arrays.stream(Material.values())
		.filter(Material::isItem)
		.forEach(material -> {
			registerFromClient(material, enchantfromlegacyid, ProtocolVersionsHelper.BEFORE_1_13);
			registerFromClient(material, dnamefromlegacytext, ProtocolVersionsHelper.BEFORE_1_13);
			registerFromClient(material, frompeenchantremapper, ProtocolVersion.MINECRAFT_PE);
		});
		//TODO FIX
		//registerFromClient(Material.MAP, new MapItemNbtToLegacyIdRemapper(), ProtocolVersion.MINECRAFT_PE);
		//registerFromClient(Material.MONSTER_EGG, new MonsterEggFromPEIdRemapper(), ProtocolVersion.MINECRAFT_PE);
		BookPagesFromPERemapper frompebook = new BookPagesFromPERemapper();
		registerFromClient(Material.WRITTEN_BOOK, frompebook, ProtocolVersion.MINECRAFT_PE);
		//TODO FIX
		//registerFromClient(Material.BOOK_AND_QUILL, frompebook, ProtocolVersion.MINECRAFT_PE);
		PotionFromPEIdRemapper frompepotion = new PotionFromPEIdRemapper();
		registerFromClient(Material.POTION, frompepotion, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.SPLASH_POTION, frompepotion, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.LINGERING_POTION, frompepotion, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.TIPPED_ARROW, frompepotion, ProtocolVersion.MINECRAFT_PE);
		LeatherArmorFromPERemapper frompeleatherarmor = new LeatherArmorFromPERemapper();
		registerFromClient(Material.LEATHER_HELMET, frompeleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.LEATHER_CHESTPLATE, frompeleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.LEATHER_LEGGINGS, frompeleatherarmor, ProtocolVersion.MINECRAFT_PE);
		registerFromClient(Material.LEATHER_BOOTS, frompeleatherarmor, ProtocolVersion.MINECRAFT_PE);
		//TODO FIX
		//FireworkFromPETagRemapper frompefireworks = new FireworkFromPETagRemapper();
		//registerFromClient(Material.FIREWORK_CHARGE, frompefireworks, ProtocolVersion.MINECRAFT_PE);
		//registerFromClient(Material.FIREWORK, frompefireworks, ProtocolVersion.MINECRAFT_PE);
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

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale,  NetworkItemStack itemstack) {
		return remapComplex(toClientRemapper, version, locale, itemstack);
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		return remapComplex(fromClientRemapper, version, locale, itemstack);
	}

}
