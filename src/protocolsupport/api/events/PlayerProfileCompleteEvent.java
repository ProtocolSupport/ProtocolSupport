package protocolsupport.api.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.ProfileProperty;

/**
 * This event is fired after player profile complete (either after doing online-mode checks, or after generating offline-mode profile)
 */
public class PlayerProfileCompleteEvent extends PlayerAbstractLoginEvent {

	public PlayerProfileCompleteEvent(Connection connection) {
		super(connection);
		connection.getProfile().getPropertiesNames().forEach(name -> properties.put(name, connection.getProfile().getProperties(name)));
	}

	protected String forcedName;
	protected UUID forcedUUID;

	/**
	 * Returns forced name or null if not set <br>
	 * By default forced name is not set
	 * @return forced name or null
	 */
	public String getForcedName() {
		return forcedName;
	}

	/**
	 * Sets forced name
	 * @param name forced name
	 */
	public void setForcedName(String name) {
		this.forcedName = name;
	}

	/**
	 * Returns forced uuid or null if not set <br>
	 * By default forced uuid is not set
	 * @return forced name or null
	 */
	public UUID getForcedUUID() {
		return forcedUUID;
	}

	/**
	 * Sets forced uuid
	 * @param uuid forced uuid
	 */
	public void setForcedUUID(UUID uuid) {
		this.forcedUUID = uuid;
	}

	protected final Map<String, Set<ProfileProperty>> properties = new HashMap<>();

	/**
	 * Returns properties
	 * @return properties
	 */
	public Map<String, Set<ProfileProperty>> getProperties() {
		return properties;
	}

	/**
	 * Returns properties by name
	 * @param name property name
	 * @return properties
	 */
	public Set<ProfileProperty> getProperties(String name) {
		return properties.getOrDefault(name, Collections.emptySet());
	}

	/**
	 * Removes properties by name
	 * @param name property name
	 */
	public void removeProperties(String name) {
		properties.remove(name);
	}

	/**
	 * Removes property
	 * @param property property
	 */
	public void removeProperty(ProfileProperty property) {
		String propertyName = property.getName();
		Set<ProfileProperty> propertiesWithName = properties.get(propertyName);
		propertiesWithName.remove(property);
		if (propertiesWithName.isEmpty()) {
			properties.remove(propertyName);
		}
	}

	/**
	 * Adds property
	 * @param property property
	 */
	public void addProperty(ProfileProperty property) {
		properties.computeIfAbsent(property.getName(), k -> new HashSet<>()).add(property);
	}


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
