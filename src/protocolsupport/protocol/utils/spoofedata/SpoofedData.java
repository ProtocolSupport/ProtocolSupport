package protocolsupport.protocol.utils.spoofedata;

import java.util.Collection;
import java.util.UUID;

import protocolsupport.api.utils.ProfileProperty;

public class SpoofedData {

	private final String hostname;
	private final String address;
	private final UUID uuid;
	private final Collection<ProfileProperty> properties;

	private final boolean failed;
	private final String failMessage;

	public SpoofedData(String hostname, String address, UUID uuid, Collection<ProfileProperty> properties) {
		this.hostname = hostname;
		this.address = address;
		this.uuid = uuid;
		this.properties = properties;
		this.failed = false;
		this.failMessage = null;
	}

	public SpoofedData(String failMessage) {
		this.hostname = null;
		this.address = null;
		this.uuid = null;
		this.properties = null;
		this.failed = true;
		this.failMessage = failMessage;
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

	public Collection<ProfileProperty> getProperties() {
		return properties;
	}

	public boolean isFailed() {
		return failed;
	}

	public String getFailMessage() {
		return failMessage;
	}

}
