package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class PlayerHeadToLegacyUUIDComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		return remap(tag);
	}

	public static NBTCompound remap(NBTCompound tag) {
		NBTCompound gameprofileTag = tag.getCompoundTagOrNull(CommonNBT.PLAYERHEAD_PROFILE);
		if (gameprofileTag != null) {
			UUID uuid = CommonNBT.deserializeUUID(gameprofileTag.getTagOfTypeOrNull(GameProfileSerializer.UUID_KEY, NBTIntArray.class));
			if (uuid != null) {
				gameprofileTag.setTag(GameProfileSerializer.UUID_KEY, new NBTString(uuid.toString()));
			}
		}
		return tag;
	}

}
