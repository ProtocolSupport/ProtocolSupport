package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.ItemMaterialLookup;

public class PotionFromLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		String name = LegacyPotionId.fromLegacyId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTCompound tag = itemstack.getNBT();
			if (tag == null) {
				tag = new NBTCompound();
				itemstack.setNBT(tag);
			}
			tag.setTag("Potion", new NBTString(name));
			itemstack.setTypeId(ItemMaterialLookup.getRuntimeId(LegacyPotionId.isThrowable(data) ? Material.SPLASH_POTION : Material.POTION));
		}
		return itemstack;
	}

}
