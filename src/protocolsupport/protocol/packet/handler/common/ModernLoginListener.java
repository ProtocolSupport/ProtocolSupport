package protocolsupport.protocol.packet.handler.common;

import javax.crypto.SecretKey;

import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ModernLoginListener extends AbstractLoginListener {

	private boolean hasCompression;
	public ModernLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname);
		this.hasCompression = hasCompression;
	}

	@Override
	protected boolean hasCompression() {
		return hasCompression;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.enableEncryption(loginKey);
	}

}
