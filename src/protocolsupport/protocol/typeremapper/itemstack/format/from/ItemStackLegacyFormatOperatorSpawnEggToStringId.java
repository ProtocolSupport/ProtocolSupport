package protocolsupport.protocol.typeremapper.itemstack.format.from;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorSpawnEggToStringId implements ItemStackLegacyFormatOperator {

	protected final String stringId;

	public ItemStackLegacyFormatOperatorSpawnEggToStringId(String stringRegistryId) {
		this.stringId = stringRegistryId;
	}

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		NBTCompound entityTag = new NBTCompound();
		entityTag.setTag("id", new NBTString(stringId));
		CommonNBT.getOrCreateRootTag(itemstack).setTag("EntityTag", entityTag);
		return itemstack;
	}

}
