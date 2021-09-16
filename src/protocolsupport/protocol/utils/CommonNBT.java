package protocolsupport.protocol.utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;

import java.util.StringJoiner;
import java.util.UUID;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBT;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.minecraftdata.MinecraftPotionData;

public class CommonNBT {

	private CommonNBT() {
	}


	public static NBTCompound getOrCreateRootTag(NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			tag = new NBTCompound();
			itemstack.setNBT(tag);
		}
		return tag;
	}


	public static final String BLOCK_TAG = "BlockEntityTag";


	public static final String DISPLAY = "display";
	public static final String DISPLAY_NAME = "Name";
	public static final String DISPLAY_LORE = "Lore";

	public static NBTCompound getOrCreateDisplayTag(NBTCompound rootTag) {
		NBTCompound display = rootTag.getCompoundTagOrNull(DISPLAY);
		if (display == null) {
			display = new NBTCompound();
			rootTag.setTag(DISPLAY, display);
		}
		return display;
	}

	public static final String ATTRIBUTES = "AttributeModifiers";
	public static final String ATTRIBUTE_ID = "AttributeName";
	public static final String ATTRIBUTE_UUID = "UUID";


	public static String[] getSignLines(NBTCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getStringTagValueOrDefault("Text" + (i + 1), "");
		}
		return lines;
	}

	public static final String BOOK_PAGES = "pages";
	public static final String BOOK_TITLE = "title";

	public static String[] getBookPages(NBTCompound tag) {
		List<NBTString> pagesTags = tag.getStringListTagOrThrow(CommonNBT.BOOK_PAGES).getTags();
		String[] pages = new String[pagesTags.size()];
		for (int i = 0; i < pagesTags.size(); i++) {
			pages[i] = pagesTags.get(i).getValue();
		}
		return pages;
	}

	public static final String BOOK_ENCHANTMENTS = "StoredEnchantments";

	public static final String MODERN_ENCHANTMENTS = "Enchantments";

	public static final String LEGACY_ENCHANTMENTS = "ench";

	@SuppressWarnings("deprecation")
	public static final Enchantment FAKE_ENCHANTMENT = new Enchantment(new NamespacedKey("ps", "fake")) {
		public boolean isTreasure() {
			return false;
		}
		public boolean isTradeable() {
			return false;
		}
		public boolean isDiscoverable() {
			return false;
		}
		public boolean isCursed() {
			return false;
		}
		public int getStartLevel() {
			return 0;
		}
		public int getMaxLevel() {
			return 0;
		}
		public EnchantmentRarity getRarity() {
			return EnchantmentRarity.COMMON;
		}
		public String getName() {
			return getKey().toString();
		}
		public EnchantmentTarget getItemTarget() {
			return EnchantmentTarget.WEAPON;
		}
		public float getDamageIncrease(int level, EntityCategory entityCategory) {
			return 0;
		}
		public Set<EquipmentSlot> getActiveSlots() {
			return Collections.emptySet();
		}
		public Component displayName(int level) {
			return Component.text(getKey().toString());
		}
		public boolean conflictsWith(Enchantment other) {
			return false;
		}
		public boolean canEnchantItem(ItemStack item) {
			return false;
		}
	};

	public static NBTCompound createFakeEnchantmentTag() {
		NBTCompound tag = new NBTCompound();
		tag.setTag("id", new NBTString(FAKE_ENCHANTMENT.getKey().toString()));
		tag.setTag("lvl", new NBTByte((byte) 1));
		return tag;
	}

	public static final String MOB_SPAWNER_SPAWNDATA = "SpawnData";

	public static NetworkEntityType getSpawnedMobType(NBTCompound spawndataTag) {
		return NetworkEntityType.getByRegistrySTypeId(spawndataTag.getStringTagValueOrNull("id"));
	}


	public static final String POTION_TYPE = "Potion";

	public static String getPotionEffectType(NBTCompound tag) {
		NBTString potionType = tag.getStringTagOrNull(POTION_TYPE);
		if (potionType != null) {
			return potionType.getValue();
		}
		NBTList<NBTCompound> customPotionEffects = tag.getCompoundListTagOrNull("CustomPotionEffects");
		if (customPotionEffects != null) {
			for (NBTCompound customPotionEffect : customPotionEffects.getTags()) {
				NBTNumber potionId = customPotionEffect.getNumberTagOrNull("Id");
				if (potionId != null) {
					return MinecraftPotionData.getNameById(potionId.getAsInt());
				}
			}
		}
		return null;
	}


	public static final String ITEM_DAMAGE = "Damage";


	public static final String MAP_ID = "map";


	public static final String BANNER_BASE = "Base";

	public static final String BANNER_PATTERNS = "Patterns";

	public static final String BANNER_PATTERN_COLOR = "Color";


	public static final String PLAYERHEAD_PROFILE = "SkullOwner";


	public static final String ITEMSTACK_STORAGE_ID = "id";
	public static final String ITEMSTACK_STORAGE_COUNT = "Count";
	public static final String ITEMSTACK_STORAGE_NBT = "tag";

	public static NetworkItemStack deserializeItemStackFromNBT(NBTCompound rootTag) {
		NetworkItemStack itemstack = new NetworkItemStack();
		NBTString idTag = rootTag.getStringTagOrNull(ITEMSTACK_STORAGE_ID);
		if (idTag != null) {
			itemstack.setTypeId(ItemMaterialLookup.getRuntimeId(ItemMaterialLookup.getByKey(idTag.getValue())));
		}
		NBTNumber countTag = rootTag.getNumberTagOrNull(ITEMSTACK_STORAGE_COUNT);
		if (countTag != null) {
			itemstack.setAmount(countTag.getAsInt());
		}
		NBTCompound tagTag = rootTag.getCompoundTagOrNull(ITEMSTACK_STORAGE_NBT);
		if (tagTag != null) {
			itemstack.setNBT(tagTag);
		}
		return itemstack;
	}

	public static NBTCompound serializeItemStackToNBT(NetworkItemStack itemstack) {
		NBTCompound rootTag = new NBTCompound();
		rootTag.setTag(ITEMSTACK_STORAGE_ID, new NBTString(ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()).getKey().toString()));
		rootTag.setTag(ITEMSTACK_STORAGE_COUNT, new NBTByte((byte) itemstack.getAmount()));
		rootTag.setTag(ITEMSTACK_STORAGE_NBT, itemstack.getNBT());
		return rootTag;
	}

	public static String deserializeBlockDataFromNBT(NBTCompound compound) {
		String name = compound.getStringTagValueOrThrow("Name");
		NBTCompound propertiesTag = compound.getCompoundTagOrNull("Properties");
		if (propertiesTag == null) {
			return name;
		} else {
			StringJoiner joiner = new StringJoiner(",", name + "[", "]");
			for (Entry<String, NBT> entry : propertiesTag.getTags().entrySet()) {
				NBT valueTag = entry.getValue();
				if (valueTag instanceof NBTString stringTag) {
					joiner.add(entry.getKey() + "=" + stringTag.getValue());
				}
			}
			return joiner.toString();
		}
	}

	public static NBTIntArray serializeUUID(UUID uuid) {
		long most = uuid.getMostSignificantBits();
		long least = uuid.getLeastSignificantBits();
		return new NBTIntArray(new int[] {(int) (most >>> 32), (int) most, (int) least >>> 32, (int) least});
	}

	public static UUID deserializeUUID(NBTIntArray tag) {
		if (tag == null) {
			return null;
		}

		int[] array = tag.getValue();
		try {
			return new UUID(
				(((long) array[0]) << 32L) | Integer.toUnsignedLong(array[1]),
				(((long) array[2]) << 32L) | Integer.toUnsignedLong(array[3])
			);
		} catch (IllegalArgumentException t) {
			return null;
		}
	}

}
