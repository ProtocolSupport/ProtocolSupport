package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggToLegacyIdSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@SuppressWarnings("deprecation")
	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		String entityId = tag.getCompound("EntityTag").getString("id");
		if (!entityId.isEmpty()) {
			if (entityId.startsWith("minecraft:")) {
				entityId = entityId.substring("minecraft:".length());
			}
			EntityType type = EntityType.fromName(entityId);
			if (type != null) {
				itemstack.setData(type.getTypeId());
			}
		}
		return tag;
	}

}
