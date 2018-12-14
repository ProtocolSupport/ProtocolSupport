package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;

public class NBTStashRemapper extends ItemStackNBTComplexRemapper {
	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		tag.setTag(CommonNBT.NBT_STASH, tag.clone());
		tag.setTag(CommonNBT.ID_STASH, new NBTInt(itemstack.getTypeId()));
		return tag;
	}
}
