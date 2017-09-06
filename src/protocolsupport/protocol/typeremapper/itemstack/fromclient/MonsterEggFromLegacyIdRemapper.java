package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggFromLegacyIdRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NetworkEntityType type = NetworkEntityType.getByRegistryITypeId(itemstack.getData());
		if (type != NetworkEntityType.NONE) {
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setTag(tag);
			}
			NBTTagCompoundWrapper nbtEntity = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			nbtEntity.setString("id", type.getRegistrySTypeId());
			tag.setCompound("EntityTag", nbtEntity);
			itemstack.setData(0);
		}
		return itemstack;
	}

}