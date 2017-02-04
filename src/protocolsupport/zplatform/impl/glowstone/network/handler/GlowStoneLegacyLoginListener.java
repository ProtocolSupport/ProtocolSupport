package protocolsupport.zplatform.impl.glowstone.network.handler;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneLegacyLoginListener extends GlowStoneLoginListener {

	public GlowStoneLegacyLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
		super(networkmanager, hostname, false);
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.getChannel().pipeline().addBefore(GlowStoneChannelHandlers.FRAMING, GlowStoneChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getCipher(Cipher.DECRYPT_MODE, key)));
	}

}
