package protocolsupport.zplatform.impl.spigot.network.handler;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLegacyLoginListener extends SpigotLoginListener {

	public SpigotLegacyLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
		super(networkmanager, hostname, false);
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.getChannel().pipeline().addBefore(SpigotChannelHandlers.SPLITTER, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getCipher(Cipher.DECRYPT_MODE, key)));
	}

}
