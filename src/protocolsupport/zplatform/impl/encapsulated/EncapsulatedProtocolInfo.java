package protocolsupport.zplatform.impl.encapsulated;

import java.net.InetSocketAddress;

import protocolsupport.api.ProtocolVersion;

public class EncapsulatedProtocolInfo {

	private final ProtocolVersion version;
	private final boolean hasCompression;
	private final InetSocketAddress address;

	public EncapsulatedProtocolInfo(InetSocketAddress address, boolean hasCompression, ProtocolVersion version) {
		this.address = address;
		this.version = version;
		this.hasCompression = hasCompression;
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public boolean hasCompression() {
		return hasCompression;
	}

	public ProtocolVersion getVersion() {
		return version;
	}

}