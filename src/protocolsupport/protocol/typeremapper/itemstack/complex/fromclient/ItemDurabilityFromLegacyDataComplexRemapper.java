package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemDurabilityFromLegacyDataComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			tag = new NBTCompound();
			itemstack.setNBT(tag);
		}
		tag.setTag(CommonNBT.ITEM_DAMAGE, new NBTInt(itemstack.getLegacyData()));
		return itemstack;
	}

}
