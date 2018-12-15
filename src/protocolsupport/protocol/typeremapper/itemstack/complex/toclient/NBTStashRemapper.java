package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;

public class NBTStashRemapper implements ItemStackComplexRemapper {
	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		NBTCompound newTag;
		if (tag != null) {
			newTag = tag;
			newTag.setTag(CommonNBT.NBT_STASH, tag.clone());
		} else { //create new compound just for the id
			newTag = new NBTCompound();
		}
		newTag.setTag(CommonNBT.ID_STASH, new NBTInt(itemstack.getTypeId()));
		itemstack.setNBT(newTag);
		return itemstack;
	}
}
