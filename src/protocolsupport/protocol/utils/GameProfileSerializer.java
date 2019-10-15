package protocolsupport.protocol.utils;

import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.authlib.GameProfile;

public class GameProfileSerializer {

	private static final String NAME_KEY = "Name";
	private static final String UUID_KEY = "Id";
	private static final String PROPERTIES_KEY = "Properties";
	private static final String PROPERTY_VALUE_KEY = "Value";
	private static final String PROPERTY_SIGNATURE_KEY = "Signature";

	public static NBTCompound serialize(GameProfile gameProfile) {
		NBTCompound tag = new NBTCompound();
		if (!StringUtils.isEmpty(gameProfile.getName())) {
			tag.setTag(NAME_KEY, new NBTString(gameProfile.getName()));
		}
		if (gameProfile.getUUID() != null) {
			tag.setTag(UUID_KEY, new NBTString(gameProfile.getUUID().toString()));
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

	public static GameProfile deserialize(NBTCompound tag) {
		String name = NBTString.getValueOrNull(tag.getTagOfType(NAME_KEY, NBTType.STRING));
		UUID uuid = null;
		try {
			uuid = UUID.fromString(NBTString.getValueOrNull(tag.getTagOfType(UUID_KEY, NBTType.STRING)));
		} catch (Throwable t) {
		}
		if (StringUtils.isEmpty(name) && (uuid == null)) {
			return null;
		}
		GameProfile gameProfile = new GameProfile(uuid, name);
		NBTCompound propertiesTag = tag.getTagOfType(PROPERTIES_KEY, NBTType.COMPOUND);
		if (propertiesTag != null) {
			for (String propertyName : propertiesTag.getTagNames()) {
				NBTList<NBTCompound> propertiesListTag = propertiesTag.getTagListOfType(propertyName, NBTType.COMPOUND);
				if (propertiesListTag != null) {
					for (NBTCompound propertyTag : propertiesListTag.getTags()) {
						gameProfile.addProperty(new ProfileProperty(
							propertyName,
							NBTString.getValueOrNull(propertyTag.getTagOfType(PROPERTY_VALUE_KEY, NBTType.STRING)),
							NBTString.getValueOrNull(propertyTag.getTagOfType(PROPERTY_SIGNATURE_KEY, NBTType.STRING))
						));
					}
				}
			}
		}
		return gameProfile;
	}

}
