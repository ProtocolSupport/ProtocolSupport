package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;

public class SpawnEggToStringIdComplexRemapper implements ItemStackComplexRemapper {

	protected final String stringId;
	public SpawnEggToStringIdComplexRemapper(String stringRegistryId) {
		this.stringId = stringRegistryId;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound entityTag = new NBTCompound();
		entityTag.setTag("id", new NBTString(stringId));
		CommonNBT.getOrCreateRootTag(itemstack).setTag("EntityTag", entityTag);
		return itemstack;
	}

}
