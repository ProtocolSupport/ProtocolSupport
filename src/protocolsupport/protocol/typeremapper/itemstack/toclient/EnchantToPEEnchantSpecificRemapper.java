package protocolsupport.protocol.typeremapper.itemstack.toclient;

import org.bukkit.enchantments.Enchantment;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

@SuppressWarnings("deprecation")
public class EnchantToPEEnchantSpecificRemapper extends ItemStackNBTSpecificRemapper {

	private static final RemappingTable.HashMapBasedIdRemappingTable ENCHANT = new RemappingTable.HashMapBasedIdRemappingTable();

	static {
		ENCHANT.setRemap(Enchantment.OXYGEN.getId(), 6);
		ENCHANT.setRemap(Enchantment.WATER_WORKER.getId(), 8);
		ENCHANT.setRemap(Enchantment.THORNS.getId(), 5);
		ENCHANT.setRemap(Enchantment.DEPTH_STRIDER.getId(), 7);
		ENCHANT.setRemap(Enchantment.FROST_WALKER.getId(), 25);
		ENCHANT.setRemap(Enchantment.DAMAGE_ALL.getId(), 9);
		ENCHANT.setRemap(Enchantment.DAMAGE_UNDEAD.getId(), 10);
		ENCHANT.setRemap(Enchantment.DAMAGE_ARTHROPODS.getId(), 11);
		ENCHANT.setRemap(Enchantment.KNOCKBACK.getId(), 12);
		ENCHANT.setRemap(Enchantment.FIRE_ASPECT.getId(), 13);
		ENCHANT.setRemap(Enchantment.LOOT_BONUS_MOBS.getId(), 14);
		ENCHANT.setRemap(Enchantment.DIG_SPEED.getId(), 15);
		ENCHANT.setRemap(Enchantment.SILK_TOUCH.getId(), 16);
		ENCHANT.setRemap(Enchantment.DURABILITY.getId(), 17);
		ENCHANT.setRemap(Enchantment.LOOT_BONUS_BLOCKS.getId(), 18);
		ENCHANT.setRemap(Enchantment.ARROW_DAMAGE.getId(), 19);
		ENCHANT.setRemap(Enchantment.ARROW_KNOCKBACK.getId(), 20);
		ENCHANT.setRemap(Enchantment.ARROW_FIRE.getId(), 21);
		ENCHANT.setRemap(Enchantment.ARROW_INFINITE.getId(), 22);
		ENCHANT.setRemap(Enchantment.LUCK.getId(), 23);
		ENCHANT.setRemap(Enchantment.LURE.getId(), 24);
		ENCHANT.setRemap(Enchantment.MENDING.getId(), 26);
	}

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("ench", NBTTagType.LIST)) {
			tag.setList("ench", remapEnchantList(tag.getList("ench", NBTTagType.COMPOUND)));
		}
		if (tag.hasKeyOfType("stored-enchants", NBTTagType.LIST)) {
			tag.setList("stored-enchants", remapEnchantList(tag.getList("stored-enchants", NBTTagType.COMPOUND)));
		}
		return tag;
	}

	private NBTTagListWrapper remapEnchantList(NBTTagListWrapper oldList) {
		NBTTagListWrapper newList = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			enchData.setShort("id", ENCHANT.getRemap(enchData.getIntNumber("id")));
			newList.addCompound(enchData);
		}
		return newList;
	}

}
