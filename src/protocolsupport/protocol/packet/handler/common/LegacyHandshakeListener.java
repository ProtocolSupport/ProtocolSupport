package protocolsupport.protocol.packet.handler.common;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;

public class LegacyHandshakeListener extends AbstractHandshakeListener {

	public LegacyHandshakeListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManager networkManager, String hostname) {
		return new LegacyLoginListener(networkManager, hostname);
	}

}
