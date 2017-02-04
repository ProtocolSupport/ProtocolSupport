package protocolsupport.zplatform.impl.glowstone.network.handler;

import io.netty.channel.Channel;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
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
	protected void enableCompression(int compressionLevel) {
		Channel channel = networkManager.getChannel();
		if (compressionLevel >= 0) {
			channel.pipeline()
			.addAfter(SpigotChannelHandlers.SPLITTER, "decompress", new SpigotPacketDecompressor(compressionLevel))
			.addAfter(SpigotChannelHandlers.PREPENDER, "compress", new SpigotPacketCompressor(compressionLevel));
		}
	}

	@Override
	protected AbstractLoginListenerPlay getLoginListenerPlay() {
		throw new UnsupportedOperationException("Can't switch to play state yet");
	}

}
