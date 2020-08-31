package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class PlayerHeadToLegacyOwnerComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		remap(tag, "SkullOwner");
		return tag;
	}

	public static void remap(NBTCompound tag, String newtagname) {
		NBTCompound gameprofileTag = tag.getCompoundTagOrNull(CommonNBT.PLAYERHEAD_PROFILE);
		if (gameprofileTag != null) {
			tag.setTag(newtagname, gameprofileTag.getStringTagOrNull(GameProfileSerializer.NAME_KEY));
		}
	}

}
