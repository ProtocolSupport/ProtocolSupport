package protocolsupport.protocol.utils;

import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.authlib.LoginProfile;

public class GameProfileSerializer {

	public static final String NAME_KEY = "Name";
	public static final String UUID_KEY = "Id";
	public static final String PROPERTIES_KEY = "Properties";
	public static final String PROPERTY_VALUE_KEY = "Value";
	public static final String PROPERTY_SIGNATURE_KEY = "Signature";

	public static NBTIntArray serializeUUID(UUID uuid) {
		long most = uuid.getMostSignificantBits();
		long least = uuid.getLeastSignificantBits();
		return new NBTIntArray(new int[] {(int) (most >>> 32), (int) most, (int) least >>> 32, (int) least});
	}

	public static NBTCompound serialize(LoginProfile gameProfile) {
		NBTCompound tag = new NBTCompound();
		if (!StringUtils.isEmpty(gameProfile.getName())) {
			tag.setTag(NAME_KEY, new NBTString(gameProfile.getName()));
		}
		if (gameProfile.getUUID() != null) {
			tag.setTag(UUID_KEY, serializeUUID(gameProfile.getUUID()));
		}
		if (!gameProfile.getProperties().isEmpty()) {
			NBTCompound propertiesTag = new NBTCompound();
			for (Entry<String, Set<ProfileProperty>> entry : gameProfile.getProperties().entrySet()) {
				NBTList<NBTCompound> propertiesListTag = new NBTList<>(NBTType.COMPOUND);
				for (ProfileProperty property : entry.getValue()) {
					NBTCompound propertyTag = new NBTCompound();
					propertyTag.setTag(PROPERTY_VALUE_KEY, new NBTString(property.getValue()));
					if (property.hasSignature()) {
						propertyTag.setTag(PROPERTY_SIGNATURE_KEY, new NBTString(property.getSignature()));
					}
					propertiesListTag.addTag(propertyTag);
				}
				propertiesTag.setTag(entry.getKey(), propertiesListTag);
			}
			tag.setTag(PROPERTIES_KEY, propertiesTag);
		}
		return tag;
	}

	public static UUID deserializeUUID(NBTIntArray tag) {
		if (tag == null) {
			return null;
		}

		int[] array = tag.getValue();
		try {
			return new UUID(
				(((long) array[0]) << 32L) | Integer.toUnsignedLong(array[1]),
				(((long) array[2]) << 32L) | Integer.toUnsignedLong(array[3])
			);
		} catch (IllegalArgumentException t) {
			return null;
		}
	}

}
