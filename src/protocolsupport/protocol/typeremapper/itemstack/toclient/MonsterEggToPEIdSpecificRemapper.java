package protocolsupport.protocol.typeremapper.itemstack.toclient;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.EntityType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

@SuppressWarnings("deprecation")
public class MonsterEggToPEIdSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag.isNull()) {
			return itemstack;
		}
		String id = tag.getCompound("EntityTag").getString("id");

		if (StringUtils.isEmpty(id)) {
			return itemstack;
		}

		itemstack.setData(PEDataValues.getLivingEntityTypeId(NetworkEntityType.fromBukkitType(EntityType.fromName(MinecraftData.removeNamespacePrefix(id)))));
		return itemstack;
	}

}
