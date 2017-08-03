package protocolsupport.protocol.utils;

import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class GameProfileSerializer {

	private static final String NAME_KEY = "Name";
	private static final String UUID_KEY = "Id";
	private static final String PROPERTIES_KEY = "Properties";
	private static final String PROPERTY_VALUE_KEY = "Value";
	private static final String PROPERTY_SIGNATURE_KEY = "Signature";

	public static NBTTagCompoundWrapper serialize(GameProfile gameProfile) {
		NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		if (!StringUtils.isEmpty(gameProfile.getName())) {
			tag.setString(NAME_KEY, gameProfile.getName());
		}
		if (gameProfile.getUUID() != null) {
			tag.setString(UUID_KEY, gameProfile.getUUID().toString());
		}
		if (!gameProfile.getProperties().isEmpty()) {
			NBTTagCompoundWrapper propertiesTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			for (Entry<String, ProfileProperty> entry : gameProfile.getProperties().entrySet()) {
				NBTTagListWrapper propertiesListTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
				ProfileProperty property = entry.getValue();
				NBTTagCompoundWrapper propertyTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				propertyTag.setString(PROPERTY_VALUE_KEY, property.getValue());
				if (property.hasSignature()) {
					propertyTag.setString(PROPERTY_SIGNATURE_KEY, property.getSignature());
				}
				propertiesListTag.addCompound(propertyTag);
				propertiesTag.setList(entry.getKey(), propertiesListTag);
			}
			tag.setCompound(PROPERTIES_KEY, propertiesTag);
		}
		return tag;
	}

	public static GameProfile deserialize(NBTTagCompoundWrapper tag) {
		String name = null;
		if (tag.hasKeyOfType(NAME_KEY, NBTTagType.STRING)) {
			name = tag.getString(NAME_KEY);
		}
		UUID uuid = null;
		try {
			if (tag.hasKeyOfType(UUID_KEY, NBTTagType.STRING)) {
				uuid = UUID.fromString(tag.getString(UUID_KEY));
			}
		} catch (Throwable t) {
		}
		if (StringUtils.isEmpty(name) && (uuid == null)) {
			return null;
		}
		GameProfile gameProfile = new GameProfile(uuid, name);
		if (tag.hasKeyOfType(PROPERTIES_KEY, NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper compound = tag.getCompound(PROPERTIES_KEY);
			for (String propertyName : compound.getKeys()) {
				NBTTagListWrapper list = compound.getList(propertyName, NBTTagType.COMPOUND);
				for (int i = 0; i < list.size(); ++i) {
					NBTTagCompoundWrapper value = list.getCompound(i);
					String propertyValue = value.getString(PROPERTY_VALUE_KEY);
					if (value.hasKeyOfType(PROPERTY_SIGNATURE_KEY, NBTTagType.STRING)) {
						gameProfile.getProperties().put(propertyName, new ProfileProperty(propertyName, propertyValue, value.getString(PROPERTY_SIGNATURE_KEY)));
					} else {
						gameProfile.getProperties().put(propertyName, new ProfileProperty(propertyName, propertyValue));
					}
				}
			}
		}
		return gameProfile;
	}

}
