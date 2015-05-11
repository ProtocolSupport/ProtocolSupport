package protocolsupport.protocol.transformer.v_1_7;

import net.minecraft.server.v1_8_R2.NetworkManager;
import protocolsupport.protocol.transformer.AbstractHandshakeListener;

public class HandshakeListener extends AbstractHandshakeListener {

	public HandshakeListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public LoginListener getLoginListener(NetworkManager networkManager) {
		return new LoginListener(networkManager);
	}

}
