package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;

public class PotionFromPEIdRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		String name = PEPotion.fromPEId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTCompound tag = itemstack.getNBT();
			if (tag == null) {
				tag = new NBTCompound();
				itemstack.setNBT(tag);
			}
			tag.setTag("Potion", new NBTString(name));
			itemstack.setLegacyData(0);
		}
		return itemstack;
	}

}
