package protocolsupport.zplatform.impl.glowstone.network.handler;

import io.netty.channel.Channel;
import net.glowstone.net.pipeline.CompressionHandler;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;

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
	protected void enableCompression(int compressionLevel) {
		Channel channel = networkManager.getChannel();
		if (compressionLevel >= 0) {
			channel.pipeline().addAfter(GlowStoneChannelHandlers.FRAMING, "compression", new CompressionHandler(compressionLevel));
		}
	}

	@Override
	protected AbstractLoginListenerPlay getLoginListenerPlay() {
		return new GlowStoneLoginListenerPlay(networkManager, profile, isOnlineMode, hostname);
	}

}
