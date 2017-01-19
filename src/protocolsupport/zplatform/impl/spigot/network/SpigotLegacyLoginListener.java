package protocolsupport.zplatform.impl.spigot.network;

import javax.crypto.SecretKey;

import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLegacyLoginListener extends SpigotLoginListener {

	public SpigotLegacyLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
		super(networkmanager, hostname);
	}

	@Override
	protected boolean hasCompression() {
		return false;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.getChannel().pipeline().addBefore(ChannelHandlers.SPLITTER, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getDecrypter(key)));
	}

}
