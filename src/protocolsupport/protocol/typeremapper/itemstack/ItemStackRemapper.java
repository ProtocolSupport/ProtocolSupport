package protocolsupport.protocol.typeremapper.itemstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Material;

import gnu.trove.decorator.TIntObjectMapDecorator;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.BookPagesToLegacyTextSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.DragonHeadSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.EmptyBookPageAdderSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.EnchantFilterNBTSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.MonsterEggToLegacyIdSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.MonsterEggToLegacyNameSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.PlayerSkullSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.clientbound.PotionToLegacyIdSpecificRemapper;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.protocol.utils.authlib.UUIDTypeAdapter;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

@SuppressWarnings("deprecation")
public class ItemStackRemapper {

	public static final IdRemappingRegistry<HashMapBasedIdRemappingTable> ITEM_ID_REMAPPING_REGISTRY = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		{
			for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
				BlockRemapperControl ctrl = new BlockRemapperControl(version);
				for (int id = 0; id < MinecraftData.BLOCK_ID_MAX; id++) {
					for (int data = 0; data < MinecraftData.BLOCK_DATA_MAX; data++) {
						MaterialAndData remap = ctrl.getRemap(new MaterialAndData(id, data));
						registerRemapEntry(id, data, remap.getId(), remap.getData(), version);
					}
				}
			}
			registerRemapEntry(Material.BED, Material.BED, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.KNOWLEDGE_BOOK, Material.BOOK, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.SHULKER_SHELL, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.TOTEM, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BEETROOT, Material.BROWN_MUSHROOM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SOUP, Material.MUSHROOM_SOUP, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SEEDS, Material.SEEDS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT, Material.POTATO_ITEM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT_POPPED, Material.BAKED_POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DRAGONS_BREATH, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPLASH_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.LINGERING_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ELYTRA, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_CRYSTAL, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SHIELD, Material.WOOD_SWORD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPECTRAL_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.TIPPED_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_ACACIA, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_BIRCH, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_DARK_OAK, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_JUNGLE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_SPRUCE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPRUCE_DOOR_ITEM, Material.WOOD_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BIRCH_DOOR_ITEM, Material.WOOD_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.JUNGLE_DOOR_ITEM, Material.WOOD_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ACACIA_DOOR_ITEM, Material.WOOD_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DARK_OAK_DOOR_ITEM, Material.WOOD_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT, Material.RAW_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_RABBIT, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_STEW, Material.MUSHROOM_SOUP, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.MUTTON, Material.RAW_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_MUTTON, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BANNER, Material.SIGN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_SHARD, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_CRYSTALS, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_FOOT, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_HIDE, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ARMOR_STAND, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.IRON_BARDING, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.GOLD_BARDING, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.DIAMOND_BARDING, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.LEASH, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.NAME_TAG, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.EXPLOSIVE_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.HOPPER_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.REDSTONE_COMPARATOR, Material.DIODE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.NETHER_BRICK_ITEM, Material.CLAY_BRICK, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.QUARTZ, Material.FEATHER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		private void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			for (int i = 0; i < MinecraftData.ITEM_DATA_MAX; i++) {
				registerRemapEntry(from, i, to, i, versions);
			}
		}
		private void registerRemapEntry(Material from, Material to, int dataTo, ProtocolVersion... versions) {
			for (int i = 0; i < MinecraftData.ITEM_DATA_MAX; i++) {
				registerRemapEntry(from, i, to, dataTo, versions);
			}
		}
		private void registerRemapEntry(Material from, int dataFrom, Material to, int dataTo, ProtocolVersion... versions) {
			registerRemapEntry(from.getId(), dataFrom, to.getId(), dataTo, versions);
		}
		private void registerRemapEntry(int idFrom, int dataFrom, int idTo, int dataTo, ProtocolVersion... versions) {
			registerRemapEntry(MinecraftData.getItemStateFromIdAndData(idFrom, dataFrom), MinecraftData.getItemStateFromIdAndData(idTo, dataTo), versions);
		}
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	private static final TIntObjectHashMap<EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>> clientbound_remapper_registry = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>> serverbound_remapper_registry = new TIntObjectHashMap<>();

	private static void registerClientboundRemapper(Material material, ItemStackSpecificRemapper transformer, ProtocolVersion... versions) {
		registerRemapper(clientbound_remapper_registry, material, transformer, versions);
	}

//	private static void registerServerboundRemapper(Material material, ItemStackSpecificRemapper transformer, ProtocolVersion... versions) {
//		registerRemapper(serverbound_remapper_registry, material, transformer, versions);
//	}

	private static void registerRemapper(TIntObjectHashMap<EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>> registry, Material material, ItemStackSpecificRemapper transformer, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>> map = Utils.getFromMapOrCreateDefault(
			new TIntObjectMapDecorator<EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>>(clientbound_remapper_registry),
			material.getId(), new EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>(ProtocolVersion.class)
		);
		Arrays.stream(versions).forEach(version -> Utils.getFromMapOrCreateDefault(map, version, new ArrayList<ItemStackSpecificRemapper>()).add(transformer));
	}

	//Order is important because some transformers may add tags in new format
	static {
		registerClientboundRemapper(Material.MONSTER_EGG, new MonsterEggToLegacyNameSpecificRemapper(), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_9));
		registerClientboundRemapper(Material.MONSTER_EGG, new MonsterEggToLegacyIdSpecificRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		registerClientboundRemapper(Material.SKULL_ITEM, new DragonHeadSpecificRemapper(), ProtocolVersionsHelper.BEFORE_1_9);
		registerClientboundRemapper(Material.SKULL_ITEM, new PlayerSkullSpecificRemapper(), ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5));
		registerClientboundRemapper(Material.POTION, new PotionToLegacyIdSpecificRemapper(false), ProtocolVersionsHelper.BEFORE_1_9);
		registerClientboundRemapper(Material.SPLASH_POTION, new PotionToLegacyIdSpecificRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);
		registerClientboundRemapper(Material.LINGERING_POTION, new PotionToLegacyIdSpecificRemapper(true), ProtocolVersionsHelper.BEFORE_1_9);
		registerClientboundRemapper(Material.WRITTEN_BOOK, new BookPagesToLegacyTextSpecificRemapper(), ProtocolVersionsHelper.BEFORE_1_8);
		registerClientboundRemapper(Material.BOOK_AND_QUILL, new EmptyBookPageAdderSpecificRemapper(), ProtocolVersionsHelper.ALL_PC);
		EnchantFilterNBTSpecificRemapper enchantfilter = new EnchantFilterNBTSpecificRemapper();
		Arrays.stream(Material.values()).forEach(material -> registerClientboundRemapper(material, enchantfilter, ProtocolVersionsHelper.ALL_PC));
	}

	public static ItemStackWrapper remapClientbound(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		return remap(clientbound_remapper_registry, version, locale, itemstack);
	}

	public static ItemStackWrapper remapServerbound(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		return remap(serverbound_remapper_registry, version, locale, itemstack);
	}

	private static ItemStackWrapper remap(TIntObjectHashMap<EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>>> registry, ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		EnumMap<ProtocolVersion, List<ItemStackSpecificRemapper>> map = registry.get(itemstack.getTypeId());
		if (map != null) {
			List<ItemStackSpecificRemapper> transformers = map.get(version);
			if (transformers != null) {
				for (ItemStackSpecificRemapper transformer : transformers) {
					itemstack = transformer.remap(version, locale, itemstack);
				}
				return itemstack;
			}
		}
		return itemstack;
	}

	private static final GameProfile dragonHeadGameProfile = new GameProfile(UUIDTypeAdapter.fromString("d34aa2b831da4d269655e33c143f096c"), "EnderDragon");
	static {
		dragonHeadGameProfile.getProperties().put("textures", new ProfileProperty(
			"textures", "eyJ0aW1lc3RhbXAiOjE0NzE0Mzg3NTEzMjYsInByb2ZpbGVJZCI6ImQzNGFhMmI4MzFkYTRkMjY5NjU1ZTMzYzE0M2YwOTZjIiwicHJvZmlsZU5hbWUiOiJFbmRlckRyYWdvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlYzdhNGRlMDdiN2VkZWFjZTliOWI5OTY2NDRlMjFkNThjYmRhYzM5MmViZWIyNDdhY2NkMTg1MzRiNTQifX19",
			"I/XY6is7j0RTGFwuVhc4PVuWUl5RRNToNUpw6jCF+V4TzXYzsBreiLQ6IM4NSJqlPXBmSlqibblDKVQL/4LnXt24/nRNVzChZv68HXB1quGxmYcSEvOLpoUk1cmXgqWJYHA68C+r1ytoJVerGWuIwuBzQZ9GpH0yb+qg7erFQgB24cbH6hh6uB6KYLwIYLQAg3TFjILv9sVJtC3FakXmtkCV3VfRUdjhigpfKP0JR3VhLVIWeW/7E+C4QCXnGlffc3Lz8PNahXtD4qRitVokId0t1qBcL8mM1qnZ/rHlNPLST61ycY9WNlRr6P83yDw2ha8QMiRH1vI5tvdXIwV7Dkn+JxfhOOeHtGunLBVe7ZEWBZfjePr/HqZGR6F7/cwZU32uH5MdTXQ+oKWUlb6HJOXxj7DfMr/uZWjrwjzmKpSDAinwvQM/8Sf96prufvcSfhZ0yopkumpnTjivgPxsJhwFXThIyFZ3ijTClMgm5NSzmB6hJ+HsBnVkDs7eyE5eI72/ES/6SksFezmBzDOqU31QbPA2mWoOYWdyZngtnf45oFnZ7NNpDW7ZKxY7FTsPEXoON/VX516KbnQ5OERI9YUpGzyKCjyMnf0L99gwgHpx5LpawdzIwk04sqFoy796BkGJf7xH6+h+AurMIenMt4on7T4FUE1ZaJvvaqexQME="
		));
	}

	public static NBTTagCompoundWrapper createDragonHeadSkullTag() {
		return GameProfileSerializer.serialize(dragonHeadGameProfile);
	}

	public static void remapSkull(NBTTagCompoundWrapper nbttagcompound) {
		remapSkull(nbttagcompound, "SkullOwner", "SkullOwner");
		remapSkull(nbttagcompound, "Owner", "ExtraType");
	}

	private static void remapSkull(NBTTagCompoundWrapper tag, String tagname, String newtagname) {
		if (tag.hasKeyOfType(tagname, NBTTagCompoundWrapper.TYPE_COMPOUND)) {
			GameProfile gameprofile = GameProfileSerializer.deserialize(tag.getCompound(tagname));
			if (gameprofile.getName() != null) {
				tag.setString(newtagname, gameprofile.getName());
			} else {
				tag.remove(tagname);
			}
		}
	}

}
