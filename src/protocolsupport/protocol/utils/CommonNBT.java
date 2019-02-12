package protocolsupport.protocol.utils;

import java.util.Map.Entry;
import java.util.StringJoiner;

import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBT;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class CommonNBT {

	public static final String DISPLAY = "display";
	public static final String DISPLAY_NAME = "Name";
	public static final String DISPLAY_LORE = "Lore";

	public static NBTCompound getOrCreateRootTag(NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			tag = new NBTCompound();
			itemstack.setNBT(tag);
		}
		return tag;
	}

	public static NBTCompound getOrCreateDisplayTag(NBTCompound rootTag) {
		NBTCompound display = rootTag.getTagOfType(DISPLAY, NBTType.COMPOUND);
		if (display == null) {
			display = new NBTCompound();
			rootTag.setTag(DISPLAY, display);
		}
		return display;
	}

	public static final String BOOK_ENCHANTMENTS = "StoredEnchantments";

	public static final String MODERN_ENCHANTMENTS = "Enchantments";

	public static final String LEGACY_ENCHANTMENTS = "ench";

	public static final String MOB_SPAWNER_SPAWNDATA = "SpawnData";

	public static NetworkEntityType getSpawnedMobType(NBTCompound spawndataTag) {
		return NetworkEntityType.getByRegistrySTypeId(NBTString.getValueOrNull(spawndataTag.getTagOfType("id", NBTType.STRING)));
	}

	public static String[] getSignLines(NBTCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = NBTString.getValueOrDefault(tag.getTagOfType("Text" + (i + 1), NBTType.STRING), "");
		}
		return lines;
	}

	public static NetworkItemStack deserializeItemStackFromNBT(NBTCompound compound) {
		NetworkItemStack stack = new NetworkItemStack();
		stack.setTypeId(ItemMaterialLookup.getRuntimeId(ItemMaterialLookup.getByKey(compound.getTagOfType("id", NBTType.STRING).getValue())));
		stack.setAmount(compound.getNumberTag("Count").getAsByte());
		NBTCompound itemstacknbt = compound.getTagOfType("tag", NBTType.COMPOUND);
		if (itemstacknbt != null) {
			stack.setNBT(itemstacknbt);
		}
		return stack;
	}

	public static NBTCompound serializeItemStackToNBT(NetworkItemStack itemstack) {
		NBTCompound compound = new NBTCompound();
		compound.setTag("id", new NBTString(ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()).getKey().toString()));
		compound.setTag("Count", new NBTByte((byte) itemstack.getAmount()));
		NBTCompound itemstacknbt = itemstack.getNBT();
		if (itemstacknbt != null) {
			compound.setTag("tag", itemstacknbt);
		}
		return compound;
	}

	public static String deserializeBlockDataFromNBT(NBTCompound compound) {
		String name = compound.getTagOfType("Name", NBTType.STRING).getValue();
		NBTCompound properties = compound.getTagOfType("Properties", NBTType.COMPOUND);
		if (properties == null) {
			return name;
		} else {
			StringJoiner joiner = new StringJoiner(",", name + "[", "]");
			for (Entry<String, NBT> entry : properties.getTags().entrySet()) {
				NBT value = entry.getValue();
				if (value instanceof NBTString) {
					joiner.add(entry.getKey() + "=" + ((NBTString) value).getValue());
				}
			}
			return joiner.toString();
		}
	}

}
