package protocolsupport.protocol.packet.handler.common;

import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;

public class LegacyHandshakeListener extends AbstractHandshakeListener {

	public LegacyHandshakeListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public LoginListener getLoginListener(NetworkManager networkManager) {
		return new LegacyLoginListener(networkManager);
	}

}
