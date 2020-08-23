package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityAttribute;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class AttributesToLegacyComplexRemapper extends ItemStackNBTComplexRemapper {

	public static final String LEGACY_ATTRIBUTE_UUID_MOST = "UUIDMost";
	public static final String LEGACY_ATTRIBUTE_UUID_LEAST = "UUIDLeast";

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> attributesTag = tag.getTagListOfTypeOrNull(CommonNBT.ATTRIBUTES, NBTType.COMPOUND);
		if (attributesTag != null) {
			for (NBTCompound attributeTag : attributesTag.getTags()) {
				NBTString attributeIdTag = attributeTag.getTagOfTypeOrNull(CommonNBT.ATTRIBUTE_ID, NBTType.STRING);
				if (attributeIdTag != null) {
					attributeTag.setTag(CommonNBT.ATTRIBUTE_ID, new NBTString(LegacyEntityAttribute.getLegacyId(attributeIdTag.getValue())));
				}
				NBTIntArray uuidTag = attributeTag.getTagOfTypeOrNull(CommonNBT.ATTRIBUTE_UUID, NBTType.INT_ARRAY);
				if (uuidTag != null) {
					UUID uuid = CommonNBT.deserializeUUID(uuidTag);
					attributeTag.setTag(LEGACY_ATTRIBUTE_UUID_MOST, new NBTLong(uuid.getMostSignificantBits()));
					attributeTag.setTag(LEGACY_ATTRIBUTE_UUID_LEAST, new NBTLong(uuid.getMostSignificantBits()));
				}
			}
		}
		return tag;
	}

}
