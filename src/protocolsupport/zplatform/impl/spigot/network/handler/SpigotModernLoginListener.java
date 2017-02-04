package protocolsupport.zplatform.impl.spigot.network.handler;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotModernLoginListener extends SpigotLoginListener {

	public SpigotModernLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname, hasCompression);
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.getChannel().pipeline()
		.addBefore(SpigotChannelHandlers.SPLITTER, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getCipher(Cipher.DECRYPT_MODE, key)))
		.addBefore(SpigotChannelHandlers.PREPENDER, ChannelHandlers.ENCRYPT, new PacketEncrypter(MinecraftEncryption.getCipher(Cipher.ENCRYPT_MODE, key)));
	}

}
