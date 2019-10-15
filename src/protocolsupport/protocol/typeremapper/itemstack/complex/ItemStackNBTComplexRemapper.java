package protocolsupport.protocol.typeremapper.itemstack.complex;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class ItemStackNBTComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag != null) {
			itemstack.setNBT(remapTag(version, locale, itemstack, tag));
		}
		return itemstack;
	}

	public abstract NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag);

}
