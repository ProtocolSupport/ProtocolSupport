package protocolsupport.zplatform.impl.spigot.network.handler;

import java.security.PrivateKey;

import javax.crypto.SecretKey;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.ITickable;
import net.minecraft.server.v1_14_R1.PacketLoginInCustomPayload;
import net.minecraft.server.v1_14_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_14_R1.PacketLoginInListener;
import net.minecraft.server.v1_14_R1.PacketLoginInStart;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginListener extends AbstractLoginListener implements ITickable, PacketLoginInListener {

	public SpigotLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
		super(networkmanager, hostname);
	}

	@Override
	public void tick() {
		loginTick();
	}

	@Override
	public void a(IChatBaseComponent msg) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + msg.getText());
	}

	@Override
	public void a(PacketLoginInStart packet) {
		handleLoginStart(packet.b().getName());
	}

	@Override
	public void a(PacketLoginInEncryptionBegin packet) {
		handleEncryption(new EncryptionPacketWrapper() {
			@Override
			public SecretKey getSecretKey(PrivateKey key) {
				return packet.a(key);
			}
			@Override
			public byte[] getNonce(PrivateKey key) {
				return packet.b(key);
			}
		});
	}

	@Override
	protected SpigotLoginListenerPlay getLoginListenerPlay() {
		return new SpigotLoginListenerPlay(networkManager, hostname);
	}

	@Override
	public void a(PacketLoginInCustomPayload var1) {
	}

}
