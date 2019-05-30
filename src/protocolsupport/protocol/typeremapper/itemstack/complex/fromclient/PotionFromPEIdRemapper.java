package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PotionToPEIdSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;

public class PotionFromPEIdRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		if (itemstack.getTypeId() == PotionToPEIdSpecificRemapper.ID_TIPPED_ARROW) {
			data--;// We should decreased data values if item is tipped_arrow
		}
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
