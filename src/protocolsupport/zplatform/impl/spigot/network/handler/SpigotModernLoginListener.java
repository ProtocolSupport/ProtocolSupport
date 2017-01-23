package protocolsupport.zplatform.impl.spigot.network.handler;

import javax.crypto.SecretKey;

import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotModernLoginListener extends SpigotLoginListener {

	private final boolean hasCompression;
	public SpigotModernLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
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
