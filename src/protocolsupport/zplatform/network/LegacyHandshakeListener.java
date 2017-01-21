package protocolsupport.zplatform.network;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;

public abstract class LegacyHandshakeListener extends AbstractHandshakeListener {

	protected LegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

}
