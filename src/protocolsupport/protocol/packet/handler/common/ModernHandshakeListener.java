package protocolsupport.protocol.packet.handler.common;

import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;

public class ModernHandshakeListener extends AbstractHandshakeListener {

	private final boolean hasCompression;
	public ModernHandshakeListener(NetworkManager networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

	@Override
	public LoginListener getLoginListener(NetworkManager networkManager) {
		return new ModernLoginListener(networkManager, hasCompression);
	}

}
