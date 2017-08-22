package protocolsupport.zplatform.impl.glowstone.network.handler;

import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneLoginListener extends AbstractLoginListener implements GlowStoneTickableListener {

	public GlowStoneLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression, boolean fullEncryption) {
		super(networkmanager, hostname, hasCompression, fullEncryption);
	}

	@Override
	protected AbstractLoginListenerPlay getLoginListenerPlay() {
		return new GlowStoneLoginListenerPlay(networkManager, profile, isOnlineMode, hostname);
	}

}
