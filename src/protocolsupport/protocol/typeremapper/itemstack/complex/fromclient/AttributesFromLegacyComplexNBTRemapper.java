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
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class AttributesFromLegacyComplexNBTRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> attributesTag = tag.getTagListOfType(CommonNBT.ATTRIBUTES, NBTType.COMPOUND);
		if (attributesTag != null) {
			for (NBTCompound attributeTag : attributesTag.getTags()) {
				NBTString attributeIdTag = attributeTag.getTagOfType(CommonNBT.ATTRIBUTE_ID, NBTType.STRING);
				if (attributeIdTag != null) {
					attributeTag.setTag(CommonNBT.ATTRIBUTE_ID, new NBTString(LegacyEntityAttribute.getModernId(attributeIdTag.getValue())));
				}
				NBTLong uuidMostTag = tag.getTagOfType(AttributesToLegacyComplexRemapper.LEGACY_ATTRIBUTE_UUID_MOST, NBTType.LONG);
				NBTLong uuidLeastTag = tag.getTagOfType(AttributesToLegacyComplexRemapper.LEGACY_ATTRIBUTE_UUID_LEAST, NBTType.LONG);
				if ((uuidMostTag != null) && (uuidLeastTag != null)) {
					attributeTag.setTag(CommonNBT.ATTRIBUTE_UUID, CommonNBT.serializeUUID(new UUID(uuidMostTag.getAsLong(), uuidLeastTag.getAsLong())));
				}
			}
		}
		return tag;
	}

}
