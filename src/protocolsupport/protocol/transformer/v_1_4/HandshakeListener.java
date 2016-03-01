package protocolsupport.protocol.transformer.v_1_4;

import net.minecraft.server.v1_9_R1.NetworkManager;
import protocolsupport.protocol.transformer.handlers.AbstractHandshakeListener;

public class HandshakeListener extends AbstractHandshakeListener {

	public HandshakeListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public LoginListener getLoginListener(NetworkManager networkManager) {
		return new LoginListener(networkManager);
	}

}
