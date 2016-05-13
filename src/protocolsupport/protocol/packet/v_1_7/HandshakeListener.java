package protocolsupport.protocol.packet.v_1_7;

import net.minecraft.server.v1_9_R2.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;

public class HandshakeListener extends AbstractHandshakeListener {

	public HandshakeListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public LoginListener getLoginListener(NetworkManager networkManager) {
		return new LoginListener(networkManager);
	}

}
