package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.AttributesToLegacyComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityAttribute;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class AttributesFromLegacyComplexNBTRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> attributesTag = tag.getCompoundListTagOrNull(CommonNBT.ATTRIBUTES);
		if (attributesTag != null) {
			for (NBTCompound attributeTag : attributesTag.getTags()) {
				NBTString attributeIdTag = attributeTag.getStringTagOrNull(CommonNBT.ATTRIBUTE_ID);
				if (attributeIdTag != null) {
					attributeTag.setTag(CommonNBT.ATTRIBUTE_ID, new NBTString(LegacyEntityAttribute.getModernId(attributeIdTag.getValue())));
				}
				NBTLong uuidMostTag = tag.getTagOfTypeOrNull(AttributesToLegacyComplexRemapper.LEGACY_ATTRIBUTE_UUID_MOST, NBTLong.class);
				NBTLong uuidLeastTag = tag.getTagOfTypeOrNull(AttributesToLegacyComplexRemapper.LEGACY_ATTRIBUTE_UUID_LEAST, NBTLong.class);
				if ((uuidMostTag != null) && (uuidLeastTag != null)) {
					attributeTag.setTag(CommonNBT.ATTRIBUTE_UUID, CommonNBT.serializeUUID(new UUID(uuidMostTag.getAsLong(), uuidLeastTag.getAsLong())));
				}
			}
		}
		return tag;
	}

}
