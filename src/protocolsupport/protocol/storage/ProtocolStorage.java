package protocolsupport.protocol.storage;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import protocolsupport.api.ProtocolVersion;

public class ProtocolStorage {

	private static final ConcurrentHashMap<SocketAddress, ProtocolVersion> versions = new ConcurrentHashMap<SocketAddress, ProtocolVersion>(1000);

	public static final void setProtocolVersion(SocketAddress address, ProtocolVersion version) {
		versions.put(address, version);
	}

	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		ProtocolVersion version = versions.get(address);
		return version != null ? version : ProtocolVersion.UNKNOWN;
	}

	public static void clearData(SocketAddress socketAddress) {
		versions.remove(socketAddress);
	}

}
