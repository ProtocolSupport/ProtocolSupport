package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class MonsterEggFromPEIdRemapper implements ItemStackComplexRemapper {

	@SuppressWarnings("deprecation")
	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.getLegacyData() > 0) {
			NetworkEntityType type = PEDataValues.getLivingTypeFromPeNetworkId(itemstack.getLegacyData());
			if (type != null) {
				NBTTagCompoundWrapper tag = ((itemstack.getNBT() != null) && !itemstack.getNBT().isNull()) ? itemstack.getNBT() : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				NBTTagCompoundWrapper entityTag = tag.hasKeyOfType("EntityTag", NBTTagType.COMPOUND) ? tag.getCompound("EntityTag") : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				entityTag.setString("id", "minecraft:" + type.getBukkitType().getName());
				tag.setCompound("EntityTag", entityTag);
				itemstack.setNBT(tag);
			}
		}
		return itemstack;
	}

}