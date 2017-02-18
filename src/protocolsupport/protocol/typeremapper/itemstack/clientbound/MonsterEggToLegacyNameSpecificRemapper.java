package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyEntityType;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggToLegacyNameSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		NBTTagCompoundWrapper entitytag = tag.getCompound("EntityTag");
		String entityId = entitytag.getString("id");
		if (!entityId.isEmpty()) {
			entitytag.setString("id", LegacyEntityType.getLegacyName(entityId));
		}
		return tag;
	}

}
