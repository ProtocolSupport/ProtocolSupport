package protocolsupport.zplatform.network;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;

public abstract class ModernHandshakeListener extends AbstractHandshakeListener {

	protected final boolean hasCompression;
	public ModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

}
