package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class PlayerHeadToLegacyOwnerComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		remap(tag, CommonNBT.PLAYERHEAD_ITEM_PROFILE, "SkullOwner");
		return tag;
	}

	public static void remap(NBTCompound tag, String tagname, String newtagname) {
		NBTCompound gameprofileTag = tag.getTagOfTypeOrNull(tagname, NBTType.COMPOUND);
		if (gameprofileTag != null) {
			tag.setTag(newtagname, gameprofileTag.getTagOfTypeOrNull(GameProfileSerializer.NAME_KEY, NBTType.STRING));
		}
	}

}
