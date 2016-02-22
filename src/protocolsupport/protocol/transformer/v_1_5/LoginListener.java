package protocolsupport.protocol.transformer.v_1_5;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.NetworkManager;
import protocolsupport.protocol.core.ChannelHandlers;
import protocolsupport.protocol.transformer.handlers.AbstractLoginListener;

public class LoginListener extends AbstractLoginListener {

	public LoginListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	protected boolean hasCompression() {
		return false;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.channel.pipeline().addBefore(ChannelHandlers.SPLITTER, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.a(2, loginKey)));
	}


}