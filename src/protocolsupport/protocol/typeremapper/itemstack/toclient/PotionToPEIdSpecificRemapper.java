package protocolsupport.protocol.typeremapper.itemstack.toclient;

import gnu.trove.map.hash.TObjectIntHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class PotionToPEIdSpecificRemapper implements ItemStackSpecificRemapper {
	private static final TObjectIntHashMap<String> nameToPeId = new TObjectIntHashMap<String>();

	static {
		nameToPeId.put(MinecraftData.addNamespacePrefix("regeneration"), 28);
		nameToPeId.put(MinecraftData.addNamespacePrefix("swiftness"), 14);
		nameToPeId.put(MinecraftData.addNamespacePrefix("fire_resistance"), 12);
		nameToPeId.put(MinecraftData.addNamespacePrefix("poison"), 25);
		nameToPeId.put(MinecraftData.addNamespacePrefix("healing"), 21);
		nameToPeId.put(MinecraftData.addNamespacePrefix("night_vision"), 5);
		nameToPeId.put(MinecraftData.addNamespacePrefix("weakness"), 34);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strength"), 31);
		nameToPeId.put(MinecraftData.addNamespacePrefix("slowness"), 17);
		nameToPeId.put(MinecraftData.addNamespacePrefix("leaping"), 9);
		nameToPeId.put(MinecraftData.addNamespacePrefix("harming"), 23);
		nameToPeId.put(MinecraftData.addNamespacePrefix("water_breathing"), 19);
		nameToPeId.put(MinecraftData.addNamespacePrefix("invisibility"), 7);

		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_regeneration"), 30);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_leaping"), 11);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_swiftness"), 16);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_healing"), 22);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_harming"), 24);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_poison"), 27);
		nameToPeId.put(MinecraftData.addNamespacePrefix("strong_strength"), 33);

		nameToPeId.put(MinecraftData.addNamespacePrefix("long_night_vision"), 6);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_invisibility"), 8);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_leaping"), 10);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_fire_resistance"), 13);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_swiftness"), 15);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_slowness"), 18);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_water_breathing"), 20);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_poison"), 26);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_regeneration"), 29);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_strength"), 32);
		nameToPeId.put(MinecraftData.addNamespacePrefix("long_weakness"), 35);
	}

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag.isNull()) {
			return itemstack;
		}
		String potion = tag.getString("Potion");
		if (!potion.isEmpty()) {
			NBTTagListWrapper tagList = tag.getList("CustomPotionEffects", NBTTagType.COMPOUND);
			if (tagList.size() >= 1) {
				potion = PotionData.getNameById(tagList.getCompound(0).getIntNumber("Id"));
			}
			if (nameToPeId.containsKey(potion)) {
				tag.remove("Potion");
				tag.remove("CustomPotionEffects");
				itemstack.setTag(tag);
				itemstack.setData(nameToPeId.get(potion));
			}
		}
		return itemstack;
	}

}
