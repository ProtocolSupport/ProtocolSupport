package protocolsupport.protocol.transformer.handlers;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.spigotmc.SpigotConfig;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.HandshakeListener;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.LoginListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ThrottleTracker;

public abstract class AbstractHandshakeListener extends HandshakeListener {

	private static final Gson gson = new Gson();

	private final NetworkManager networkManager;

	public AbstractHandshakeListener(NetworkManager networkmanager) {
		super(MinecraftServer.getServer(), networkmanager);
		this.networkManager = networkmanager;
	}

	@Override
	public void a(final PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN: {
				networkManager.a(EnumProtocol.LOGIN);
				try {
					final InetAddress address = ((InetSocketAddress) networkManager.getSocketAddress()).getAddress();
					if (ThrottleTracker.isEnabled() && !SpigotConfig.bungee) {
						if (ThrottleTracker.isThrottled(address)) {
							final ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
							networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
							networkManager.close(chatcomponenttext);
							return;
						}
						ThrottleTracker.track(address, System.currentTimeMillis());
					}
				} catch (Throwable t) {
					LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				if (packethandshakinginsetprotocol.b() != ProtocolVersion.getLatest().getId()) {
					final ChatComponentText chatcomponenttext = new ChatComponentText("Unsupported protocol version "+packethandshakinginsetprotocol.b());
					this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
					this.networkManager.close(chatcomponenttext);
					break;
				}
				networkManager.a(getLoginListener(networkManager));
				if (SpigotConfig.bungee) {
					final String[] split = packethandshakinginsetprotocol.hostname.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						final ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
						networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
						networkManager.close(chatcomponenttext);
						return;
					}
					packethandshakinginsetprotocol.hostname = split[0];
					SocketAddress oldaddress = networkManager.getSocketAddress();
					ProtocolVersion version = ProtocolStorage.getProtocolVersion(oldaddress);
					ProtocolStorage.clearData(oldaddress);
					SocketAddress newaddress = new InetSocketAddress(split[1], ((InetSocketAddress) oldaddress).getPort());
					networkManager.l = newaddress;
					ProtocolStorage.setProtocolVersion(newaddress, version);
					networkManager.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
					if (split.length == 4) {
						networkManager.spoofedProfile = gson.fromJson(split[3], Property[].class);
					}
				}
				((LoginListener) networkManager.getPacketListener()).hostname = packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port;
				break;
			}
			case STATUS: {
				networkManager.a(EnumProtocol.STATUS);
				networkManager.a(new StatusListener(MinecraftServer.getServer(), networkManager));
				break;
			}
			default: {
				throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
			}
		}
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
	}

	public abstract LoginListener getLoginListener(NetworkManager networkManager);

}
