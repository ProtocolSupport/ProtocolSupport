package protocolsupport.zplatform.impl.glowstone.network.handler;

import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class GlowStoneLoginListener extends AbstractLoginListener implements GlowStoneTickableListener {

	private final boolean hasCompression;
	public GlowStoneLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname);
		this.hasCompression = hasCompression;
	}

	@Override
	protected boolean hasCompression() {
		return hasCompression;
	}

	@Override
	protected AbstractLoginListenerPlay getLoginListenerPlay() {
		return new GlowStoneLoginListenerPlay(networkManager, profile, isOnlineMode, hostname);
	}

}
