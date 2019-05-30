package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;

public class NBTUnStashRemapper extends ItemStackNBTComplexRemapper {
	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		itemstack.setTypeId(tag.getNumberTag(CommonNBT.ID_STASH).getAsInt());
		if (tag.getTag(CommonNBT.NBT_STASH) != null) {
			return tag.getTagOfType(CommonNBT.NBT_STASH, NBTType.COMPOUND);
		}
		return null;
	}
}
