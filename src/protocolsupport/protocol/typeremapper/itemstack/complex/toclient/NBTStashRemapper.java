package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEItems;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;

public class NBTStashRemapper implements ItemStackComplexRemapper {
	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		NBTCompound newTag = null;
		boolean needsIDStash = false;
		if (version.getProtocolType() == ProtocolType.PE) {
			int peCombined = PEItems.getPECombinedIdByModernId(itemstack.getTypeId());
			int reverseModern = PEItems.getModernIdByPECombined(peCombined);
			needsIDStash = reverseModern != itemstack.getTypeId();
		}
		if (tag != null) {
			newTag = tag;
			newTag.setTag(CommonNBT.NBT_STASH, tag.clone());
		} else if (needsIDStash) { //create new compound just for the id
			newTag = new NBTCompound();
		}
		if (newTag != null) {
			newTag.setTag(CommonNBT.ID_STASH, new NBTInt(itemstack.getTypeId()));
		}
		itemstack.setNBT(newTag);
		return itemstack;
	}
}
