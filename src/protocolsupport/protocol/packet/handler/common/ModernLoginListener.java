package protocolsupport.protocol.packet.handler.common;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;

public class ModernLoginListener extends AbstractLoginListener {

	private boolean hasCompression;
	public ModernLoginListener(NetworkManager networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname);
		this.hasCompression = hasCompression;
	}

	@Override
	protected boolean hasCompression() {
		return hasCompression;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.a(loginKey);
	}

}
