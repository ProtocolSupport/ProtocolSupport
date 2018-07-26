package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class PlayerHeadToLegacyOwnerComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		remap(tag, "SkullOwner", "SkullOwner");
		return tag;
	}

	public static void remap(NBTTagCompoundWrapper tag, String tagname, String newtagname) {
		if (tag.hasKeyOfType(tagname, NBTTagType.COMPOUND)) {
			GameProfile gameprofile = GameProfileSerializer.deserialize(tag.getCompound(tagname));
			if (gameprofile.getName() != null) {
				tag.setString(newtagname, gameprofile.getName());
			} else {
				tag.remove(tagname);
			}
		}
	}

}
