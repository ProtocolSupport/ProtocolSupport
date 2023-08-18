package protocolsupport.zplatform.impl.spigot.network.handler;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import javax.crypto.SecretKey;

import org.bukkit.Bukkit;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.util.CryptographyException;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginListener extends AbstractLoginListener implements PacketLoginInListener {

	public SpigotLoginListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public void a(IChatBaseComponent msg) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + msg.a());
	}

	@Override
	public void a(PacketLoginInStart packet) {
		handleLoginStart(packet.a());
	}

	@Override
	public void a(PacketLoginInEncryptionBegin packet) {
		handleEncryption(new EncryptionPacketWrapper() {
			@Override
			public SecretKey getSecretKey(PrivateKey key) throws GeneralSecurityException {
				try {
					return packet.a(key);
				} catch (CryptographyException e) {
					throw new GeneralSecurityException(e);
				}
			}
			@Override
			public boolean isNonceValid(byte[] nonce, PrivateKey key) throws GeneralSecurityException {
				return packet.a(nonce, key);
			}
		});
	}

	@Override
	protected SpigotLoginListenerPlay getLoginListenerPlay() {
		return new SpigotLoginListenerPlay(networkManager);
	}

	@Override
	public void a(PacketLoginInCustomPayload var1) {
	}

	@Override
	public boolean a() {
		return networkManager.isConnected();
	}

}
