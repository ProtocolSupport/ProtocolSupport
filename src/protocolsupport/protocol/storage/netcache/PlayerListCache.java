package protocolsupport.protocol.storage.netcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

public class PlayerListCache {

	protected static final PlayerListEntry unknown = new PlayerListEntry("PS|UNKNOWN", null, Collections.emptyList()) {
		@Override
		public void addProperty(ProfileProperty property) {
		};
		@Override
		public void setDisplayNameJson(String displayNameJson) {
		};
	};

	protected final HashMap<UUID, PlayerListEntry> playerlist = new HashMap<>();

	public void addEntry(UUID uuid, PlayerListEntry entry) {
		playerlist.put(uuid, entry);
	}

	public PlayerListEntry getEntry(UUID uuid) {
		return playerlist.getOrDefault(uuid, unknown);
	}

	public void removeEntry(UUID uuid) {
		playerlist.remove(uuid);
	}

	public static class PlayerListEntry {
		protected final String name;
		protected String displayNameJson;
		protected final Map<String, ProfileProperty> propSigned = new HashMap<>();
		protected final Map<String, ProfileProperty> propUnsigned = new HashMap<>();

		public PlayerListEntry(String name, String displayNameJson, Collection<ProfileProperty> properties) {
			this.name = name;
			this.displayNameJson = displayNameJson;
			properties.forEach(this::addProperty);
		}

		public void setDisplayNameJson(String displayNameJson) {
			this.displayNameJson = displayNameJson;
		}

		public String getName(String locale) {
			return displayNameJson != null ? ChatAPI.fromJSON(displayNameJson).toLegacyText(locale) : name;
		}

		public void addProperty(ProfileProperty property) {
			if (property.hasSignature()) {
				propSigned.put(property.getName(), property);
			} else {
				propUnsigned.put(property.getName(), property);
			}
		}

		public List<ProfileProperty> getProperties(boolean signedOnly) {
			if (signedOnly) {
				return new ArrayList<>(propSigned.values());
			} else {
				ArrayList<ProfileProperty> properties = new ArrayList<>();
				properties.addAll(propSigned.values());
				properties.addAll(propUnsigned.values());
				return properties;
			}
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
