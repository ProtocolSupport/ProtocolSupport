package protocolsupport.protocol.utils;

import java.util.Collection;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.NBTTagListWrapper;

public class GameProfileSerializer {

	private static final String NAME_KEY = "Name";
	private static final String UUID_KEY = "Id";
	private static final String PROPERTIES_KEY = "Properties";
	private static final String PROPERTY_VALUE_KEY = "Value";
	private static final String PROPERTY_SIGNATURE_KEY = "Signature";

	public static NBTTagCompoundWrapper serialize(GameProfile gameProfile) {
		NBTTagCompoundWrapper tag = NBTTagCompoundWrapper.createEmpty();
		if (!StringUtils.isEmpty(gameProfile.getName())) {
			tag.setString(NAME_KEY, gameProfile.getName());
		}
		if (gameProfile.getId() != null) {
			tag.setString(UUID_KEY, gameProfile.getId().toString());
		}
		if (!gameProfile.getProperties().isEmpty()) {
			NBTTagCompoundWrapper propertiesTag = NBTTagCompoundWrapper.createEmpty();
			for (Entry<String, Collection<Property>> entry : gameProfile.getProperties().asMap().entrySet()) {
				NBTTagListWrapper keyNamePropertiesTag = NBTTagListWrapper.create();
				for (Property property : entry.getValue()) {
					NBTTagCompoundWrapper propertyTag = NBTTagCompoundWrapper.createEmpty();
					propertyTag.setString(PROPERTY_VALUE_KEY, property.getValue());
					if (property.hasSignature()) {
						propertyTag.setString(PROPERTY_SIGNATURE_KEY, property.getSignature());
					}
					keyNamePropertiesTag.addCompound(propertyTag);
				}
				propertiesTag.setList(entry.getKey(), keyNamePropertiesTag);
			}
			tag.setCompound(PROPERTIES_KEY, propertiesTag);
		}
		return tag;
	}

	public static GameProfile deserialize(NBTTagCompoundWrapper tag) {
		String name = null;
		if (tag.hasKeyOfType(NAME_KEY, NBTTagCompoundWrapper.TYPE_STRING)) {
			name = tag.getString(NAME_KEY);
		}
		UUID uuid = null;
		try {
			if (tag.hasKeyOfType(UUID_KEY, NBTTagCompoundWrapper.TYPE_STRING)) {
				uuid = UUID.fromString(tag.getString(UUID_KEY));
			}
		} catch (Throwable t) {
		}
		if (StringUtils.isEmpty(name) && uuid == null) {
			return null;
		}
		GameProfile gameProfile = new GameProfile(uuid, name);
		if (tag.hasKeyOfType(PROPERTIES_KEY, NBTTagCompoundWrapper.TYPE_COMPOUND)) {
			NBTTagCompoundWrapper compound = tag.getCompound(PROPERTIES_KEY);
			for (String propertyName : compound.getKeys()) {
				NBTTagListWrapper list = compound.getList(propertyName, NBTTagCompoundWrapper.TYPE_COMPOUND);
				for (int i = 0; i < list.size(); ++i) {
					NBTTagCompoundWrapper value = list.getCompound(i);
					String propertyValue = value.getString(PROPERTY_VALUE_KEY);
					if (value.hasKeyOfType(PROPERTY_SIGNATURE_KEY, NBTTagCompoundWrapper.TYPE_STRING)) {
						gameProfile.getProperties().put(propertyName, new Property(propertyName, propertyValue, value.getString(PROPERTY_SIGNATURE_KEY)));
					} else {
						gameProfile.getProperties().put(propertyName, new Property(propertyName, propertyValue));
					}
				}
			}
		}
		return gameProfile;
	}

}
