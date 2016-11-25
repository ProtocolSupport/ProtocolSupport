package protocolsupport.protocol.packet.handler.common;

import javax.crypto.SecretKey;

import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.utils.nms.NetworkManagerWrapper;

public class LegacyLoginListener extends AbstractLoginListener {

	public LegacyLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
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
