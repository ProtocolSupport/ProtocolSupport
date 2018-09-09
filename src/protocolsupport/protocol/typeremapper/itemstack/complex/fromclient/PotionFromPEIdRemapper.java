package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class PotionFromPEIdRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		String name = PEPotion.fromPEId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTTagCompoundWrapper tag = itemstack.getNBT();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setNBT(tag);
			}
			tag.setString("Potion", name);
			itemstack.setLegacyData(0);
		}
		return itemstack;
	}

}
