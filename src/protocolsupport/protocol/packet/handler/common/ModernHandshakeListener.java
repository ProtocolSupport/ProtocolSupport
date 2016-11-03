package protocolsupport.protocol.packet.handler.common;

import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;

public class ModernHandshakeListener extends AbstractHandshakeListener {

	private final boolean hasCompression;
	public ModernHandshakeListener(NetworkManager networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManager networkManager, String hostname) {
		return new ModernLoginListener(networkManager, hostname, hasCompression);
	}

}
