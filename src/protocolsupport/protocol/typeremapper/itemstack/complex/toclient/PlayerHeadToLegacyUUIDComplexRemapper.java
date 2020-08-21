package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class PlayerHeadToLegacyUUIDComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		return remap(tag, CommonNBT.PLAYERHEAD_ITEM_PROFILE);
	}

	public static NBTCompound remap(NBTCompound tag, String tagname) {
		NBTCompound gameprofileTag = tag.getTagOfType(tagname, NBTType.COMPOUND);
		if (gameprofileTag != null) {
			UUID uuid = CommonNBT.deserializeUUID(gameprofileTag.getTagOfType(GameProfileSerializer.UUID_KEY, NBTType.INT_ARRAY));
			if (uuid != null) {
				gameprofileTag.setTag(GameProfileSerializer.UUID_KEY, new NBTString(uuid.toString()));
			}
		}
		return tag;
	}

}
