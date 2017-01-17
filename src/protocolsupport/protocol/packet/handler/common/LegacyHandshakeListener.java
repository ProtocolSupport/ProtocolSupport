package protocolsupport.protocol.packet.handler.common;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class LegacyHandshakeListener extends AbstractHandshakeListener {

	public LegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new LegacyLoginListener(networkManager, hostname);
	}

}
