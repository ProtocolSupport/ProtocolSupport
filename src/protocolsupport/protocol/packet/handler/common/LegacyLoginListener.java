package protocolsupport.protocol.packet.handler.common;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_10_R1.MinecraftEncryption;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;

public class LegacyLoginListener extends AbstractLoginListener {

	public LegacyLoginListener(NetworkManager networkmanager) {
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
