package protocolsupport.protocol.packet.v_1_5;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_9_R1.MinecraftEncryption;
import net.minecraft.server.v1_9_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;

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