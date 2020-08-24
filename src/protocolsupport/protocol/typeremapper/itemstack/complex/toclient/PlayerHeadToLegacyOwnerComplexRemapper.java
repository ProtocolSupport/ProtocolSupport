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
		remap(tag, "SkullOwner");
		return tag;
	}

	public static void remap(NBTCompound tag, String newtagname) {
		NBTCompound gameprofileTag = tag.getTagOfTypeOrNull(CommonNBT.PLAYERHEAD_PROFILE, NBTType.COMPOUND);
		if (gameprofileTag != null) {
			tag.setTag(newtagname, gameprofileTag.getTagOfTypeOrNull(GameProfileSerializer.NAME_KEY, NBTType.STRING));
		}
	}

}
