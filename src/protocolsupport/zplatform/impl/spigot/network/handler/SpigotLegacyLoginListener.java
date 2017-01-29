package protocolsupport.zplatform.impl.spigot.network.handler;

import javax.crypto.SecretKey;

import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
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
		networkManager.getChannel().pipeline().addBefore(SpigotChannelHandlers.SPLITTER, SpigotChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getDecrypter(key)));
	}

}
