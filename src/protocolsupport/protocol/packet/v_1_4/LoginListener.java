package protocolsupport.protocol.packet.v_1_4;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_9_R2.MinecraftEncryption;
import net.minecraft.server.v1_9_R2.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;

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