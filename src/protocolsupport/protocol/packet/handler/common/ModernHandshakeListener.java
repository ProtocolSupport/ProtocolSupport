package protocolsupport.protocol.packet.handler.common;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.utils.nms.NetworkManagerWrapper;

public class ModernHandshakeListener extends AbstractHandshakeListener {

	private final boolean hasCompression;
	public ModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new ModernLoginListener(networkManager, hostname, hasCompression);
	}

}
