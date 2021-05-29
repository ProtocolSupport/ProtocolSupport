package protocolsupport.protocol.utils;

import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.authlib.LoginProfile;

public class GameProfileSerializer {

	private GameProfileSerializer() {
	}

	public static final String NAME_KEY = "Name";
	public static final String UUID_KEY = "Id";
	public static final String PROPERTIES_KEY = "Properties";
	public static final String PROPERTY_VALUE_KEY = "Value";
	public static final String PROPERTY_SIGNATURE_KEY = "Signature";

	public static NBTCompound serialize(LoginProfile gameProfile) {
		NBTCompound tag = new NBTCompound();
		if (!StringUtils.isEmpty(gameProfile.getName())) {
			tag.setTag(NAME_KEY, new NBTString(gameProfile.getName()));
		}
		if (gameProfile.getUUID() != null) {
			tag.setTag(UUID_KEY, CommonNBT.serializeUUID(gameProfile.getUUID()));
		}
		if (!gameProfile.getProperties().isEmpty()) {
			NBTCompound propertiesTag = new NBTCompound();
			for (Entry<String, Set<ProfileProperty>> entry : gameProfile.getProperties().entrySet()) {
				NBTList<NBTCompound> propertiesListTag = NBTList.createCompoundList();
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

}
