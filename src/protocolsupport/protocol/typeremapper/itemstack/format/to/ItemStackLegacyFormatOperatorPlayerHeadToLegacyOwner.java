package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class ItemStackLegacyFormatOperatorPlayerHeadToLegacyOwner extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		apply(tag, "SkullOwner");
		return tag;
	}

	public static void apply(NBTCompound tag, String newtagname) {
		NBTCompound gameprofileTag = tag.getCompoundTagOrNull(CommonNBT.PLAYERHEAD_PROFILE);
		if (gameprofileTag != null) {
			tag.setTag(newtagname, gameprofileTag.getStringTagOrNull(GameProfileSerializer.NAME_KEY));
		}
	}

}
