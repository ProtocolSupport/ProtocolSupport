package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggFromLegacyIdRemapper implements ItemStackSpecificRemapper {
	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		int entityId = itemstack.getData(); // In pre-1.9 versions, the data value from the spawn egg is actually the entity ID
		EntityType type = EntityType.fromId(entityId); // ...so we get the EntityType from the ItemStack data value

		if (type != null) { // Disallow invalid spawn eggs (prevent crashes)
			// Get the ItemStack NBT tag (or create one if needed)
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setTag(tag);
			}
			NBTTagCompoundWrapper nbtEntity = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			nbtEntity.setString("id", "minecraft:" + type.getName());
			tag.setCompound("EntityTag", nbtEntity);
			itemstack.setData(0); // Reset the egg data value to 0
		}
		return itemstack;
	}
}