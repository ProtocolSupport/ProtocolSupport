package protocolsupport.protocol.typeremapper.itemstack.format.from;

import java.util.UUID;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class ItemStackLegacyFormatOperatorPlayerHeadToLegacyUUID extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		return apply(tag);
	}

	public static NBTCompound apply(NBTCompound tag) {
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
