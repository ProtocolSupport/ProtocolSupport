package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import org.bukkit.Material;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.minecraftdata.MinecraftPotionData;

public class PotionToPEIdSpecificRemapper implements ItemStackComplexRemapper {

	public static final int ID_TIPPED_ARROW = MaterialAPI.getItemNetworkId(Material.TIPPED_ARROW);

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			return itemstack;
		}
		NBTString potionTag = tag.getTagOfType("Potion", NBTType.STRING);
		if (potionTag != null && !potionTag.getValue().isEmpty()) {
			String potion = potionTag.getValue();
			NBTList<NBTCompound> tagList = tag.getTagListOfType("CustomPotionEffects", NBTType.COMPOUND);
			if (tagList != null && tagList.size() >= 1) {
				potion = MinecraftPotionData.getNameById(tagList.getTag(0).getNumberTag("Id").getAsInt());
			}
			if (PEPotion.hasPERemap(potion)) {
				tag.removeTag("Potion");
				tag.removeTag("CustomPotionEffects");
				itemstack.setNBT(tag);
				int data = PEPotion.toPEId(potion);
				if (itemstack.getTypeId() == ID_TIPPED_ARROW) {
					data++;// We should increased data values if item is tipped_arrow
				}
				itemstack.setLegacyData(data);
			}
		}
		return itemstack;
	}

}
