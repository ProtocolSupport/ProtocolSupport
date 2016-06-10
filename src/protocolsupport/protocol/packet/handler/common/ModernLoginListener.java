package protocolsupport.protocol.packet.handler.common;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;

public class ModernLoginListener extends AbstractLoginListener {

	private boolean hasCompression;
	public ModernLoginListener(NetworkManager networkmanager, boolean hasCompression) {
		super(networkmanager);
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
