package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyMonsterEgg;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggFromLegacyIdRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		String type = LegacyMonsterEgg.fromLegacyId(itemstack.getData());
		if (!StringUtils.isEmpty(type)) {
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setTag(tag);
			}
			NBTTagCompoundWrapper nbtEntity = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			nbtEntity.setString("id", type);
			tag.setCompound("EntityTag", nbtEntity);
			itemstack.setData(0);
		}
		return itemstack;
	}

}