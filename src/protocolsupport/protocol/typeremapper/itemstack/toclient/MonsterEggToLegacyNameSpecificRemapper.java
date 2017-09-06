package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityType;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MonsterEggToLegacyNameSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		NBTTagCompoundWrapper entitytag = tag.getCompound("EntityTag");
		NetworkEntityType type = NetworkEntityType.getByRegistrySTypeId(entitytag.getString("id"));
		if (type != NetworkEntityType.NONE) {
			entitytag.setString("id", LegacyEntityType.getLegacyName(type));
		}
		return tag;
	}

}
