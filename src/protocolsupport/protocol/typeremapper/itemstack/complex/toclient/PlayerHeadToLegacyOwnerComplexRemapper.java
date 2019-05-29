package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.authlib.GameProfile;

public class PlayerHeadToLegacyOwnerComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		remap(tag, "SkullOwner", "SkullOwner");
		return tag;
	}

	public static void remap(NBTCompound tag, String tagname, String newtagname) {
		NBTCompound gameprofileTag = tag.getTagOfType(tagname, NBTType.COMPOUND);
		if (gameprofileTag != null) {
			GameProfile gameprofile = GameProfileSerializer.deserialize(gameprofileTag);
			if (gameprofile.getName() != null) {
				tag.setTag(newtagname, new NBTString(gameprofile.getName()));
			} else {
				tag.removeTag(tagname);
			}
		}
	}

}
