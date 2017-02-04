package protocolsupport.zplatform.impl.glowstone.network.handler;

import javax.crypto.SecretKey;

import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneModernLoginListener extends GlowStoneLoginListener {

	public GlowStoneModernLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname, hasCompression);
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		// TODO Auto-generated method stub
		
	}

}
