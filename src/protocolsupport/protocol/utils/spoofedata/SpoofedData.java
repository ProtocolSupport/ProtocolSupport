package protocolsupport.protocol.utils.spoofedata;

import java.util.UUID;

import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;

public class SpoofedData {

	private final String hostname;
	private final String address;
	private final UUID uuid;
	private final ProfileProperty[] properties;
	public SpoofedData(String hostname, String address, UUID uuid, ProfileProperty[] properties) {
		this.hostname = hostname;
		this.address = address;
		this.uuid = uuid;
		this.properties = properties;
	}

	public String getHostname() {
		return hostname;
	}

	public String getAddress() {
		return address;
	}

	public UUID getUUID() {
		return uuid;
	}

	public ProfileProperty[] getProperties() {
		return properties;
	}

}
