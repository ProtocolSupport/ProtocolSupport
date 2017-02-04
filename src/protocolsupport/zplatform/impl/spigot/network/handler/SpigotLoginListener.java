package protocolsupport.zplatform.impl.spigot.network.handler;

import java.security.PrivateKey;

import javax.crypto.SecretKey;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.ITickable;
import net.minecraft.server.v1_11_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginInListener;
import net.minecraft.server.v1_11_R1.PacketLoginInStart;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class SpigotLoginListener extends AbstractLoginListener implements ITickable, PacketLoginInListener {

	private final boolean hasCompression;
	public SpigotLoginListener(NetworkManagerWrapper networkmanager, String hostname, boolean hasCompression) {
		super(networkmanager, hostname);
		this.hasCompression = hasCompression;
	}

	@Override
	public void F_() {
		tick();
	}

	@Override
	protected boolean hasCompression() {
		return hasCompression;
	}

	@Override
	protected void enableCompression(int compressionLevel) {
		Channel channel = networkManager.getChannel();
		if (compressionLevel >= 0) {
			channel.pipeline()
			.addAfter(SpigotChannelHandlers.SPLITTER, "decompress", new SpigotPacketDecompressor(compressionLevel))
			.addAfter(SpigotChannelHandlers.PREPENDER, "compress", new SpigotPacketCompressor(compressionLevel));
		}
	}

	@Override
	public void a(IChatBaseComponent msg) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + msg.getText());
	}

	@Override
	public void a(PacketLoginInStart packet) {
		handleLoginStart(packet.a().getName());
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
		return new SpigotLoginListenerPlay(networkManager, profile, isOnlineMode, hostname);
	}

}
