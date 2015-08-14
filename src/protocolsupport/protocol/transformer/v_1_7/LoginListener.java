package protocolsupport.protocol.transformer.v_1_7;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_8_R3.NetworkManager;
import protocolsupport.protocol.transformer.handlers.AbstractLoginListener;

public class LoginListener extends AbstractLoginListener {

	public LoginListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	protected boolean hasCompression() {
		return false;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.a(loginKey);
	}

}