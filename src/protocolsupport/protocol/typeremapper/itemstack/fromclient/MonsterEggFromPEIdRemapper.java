package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class MonsterEggFromPEIdRemapper implements ItemStackSpecificRemapper {

	@SuppressWarnings("deprecation")
	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		if (itemstack.getData() > 0) {
			NetworkEntityType type = PEDataValues.getLivingTypeFromPeNetworkId(itemstack.getData());
			if (type != null) {
				NBTTagCompoundWrapper tag = ((itemstack.getTag() != null) && !itemstack.getTag().isNull()) ? itemstack.getTag() : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				NBTTagCompoundWrapper entityTag = tag.hasKeyOfType("EntityTag", NBTTagType.COMPOUND) ? tag.getCompound("EntityTag") : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				entityTag.setString("id", MinecraftData.addNamespacePrefix(type.getBukkitType().getName()));
				tag.setCompound("EntityTag", entityTag);
				itemstack.setTag(tag);
			}
		}
		return itemstack;
	}

}