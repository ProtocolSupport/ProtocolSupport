package protocolsupport.protocol.utils.spoofedata;

import java.util.Collection;
import java.util.UUID;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.ReflectionUtils;

public class SpoofedData {

	public static SpoofedData createFailed(String failmessage) {
		return new SpoofedData(null, null, null, null, failmessage);
	}

	public static SpoofedData createEmpty(String hostname) {
		return new SpoofedData(hostname, null, null, null, null);
	}

	public static SpoofedData create(String hostname, String address, UUID uuid, Collection<ProfileProperty> properties) {
		return new SpoofedData(hostname, address, uuid, properties, null);
	}

	private final String hostname;
	private final String address;
	private final UUID uuid;
	private final Collection<ProfileProperty> properties;

	private final String failMessage;

	protected SpoofedData(String hostname, String address, UUID uuid, Collection<ProfileProperty> properties, String failmessage) {
		this.hostname = hostname;
		this.address = address;
		this.uuid = uuid;
		this.properties = properties;
		this.failMessage = failmessage;
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
		return failMessage != null;
	}

	public String getFailMessage() {
		return failMessage;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
