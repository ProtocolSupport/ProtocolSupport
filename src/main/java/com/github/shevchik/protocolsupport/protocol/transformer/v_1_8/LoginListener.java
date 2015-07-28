package com.github.shevchik.protocolsupport.protocol.transformer.v_1_8;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_8_R3.NetworkManager;
import com.github.shevchik.protocolsupport.protocol.transformer.AbstractLoginListener;

public class LoginListener extends AbstractLoginListener {

	public LoginListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	protected boolean hasCompression() {
		return true;
	}

	@Override
	protected void enableEncryption(SecretKey key) {
		networkManager.a(loginKey);
	}

}