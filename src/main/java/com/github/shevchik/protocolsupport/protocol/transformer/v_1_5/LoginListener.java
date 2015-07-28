package com.github.shevchik.protocolsupport.protocol.transformer.v_1_5;

import javax.crypto.SecretKey;

import com.github.shevchik.protocolsupport.protocol.core.ChannelHandlers;
import com.github.shevchik.protocolsupport.protocol.transformer.AbstractLoginListener;
import com.github.shevchik.protocolsupport.protocol.transformer.v_1_5.serverboundtransformer.PacketDecrypter;
import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.NetworkManager;

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