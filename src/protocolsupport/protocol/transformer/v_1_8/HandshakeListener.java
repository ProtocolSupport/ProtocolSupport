package protocolsupport.protocol.transformer.v_1_8;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import net.minecraft.server.v1_8_R2.ChatComponentText;
import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R2.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R2.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R2.PacketStatusListener;

import org.apache.logging.log4j.LogManager;
import org.spigotmc.SpigotConfig;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ThrottleTracker;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

public class HandshakeListener implements PacketHandshakingInListener {

	private static final Gson gson = new Gson();

	private final NetworkManager networkManager;

	public HandshakeListener(final NetworkManager networkmanager) {
		networkManager = networkmanager;
	}

	@Override
	public void a(final PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN: {
				networkManager.a(EnumProtocol.LOGIN);
				try {
					final InetAddress address = ((InetSocketAddress) networkManager.getSocketAddress()).getAddress();
					if (ThrottleTracker.isEnabled() && !"127.0.0.1".equals(address.getHostAddress())) {
						if (ThrottleTracker.isThrottled(address)) {
							final ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
							networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
							networkManager.close(chatcomponenttext);
						}
						ThrottleTracker.track(address, System.currentTimeMillis());
					}
				} catch (Throwable t) {
					LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				networkManager.a(new LoginListener(networkManager));
				if (SpigotConfig.bungee) {
					final String[] split = packethandshakinginsetprotocol.b.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						final ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
						networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
						networkManager.close(chatcomponenttext);
						return;
					}
					packethandshakinginsetprotocol.b = split[0];
					ProtocolVersion version = ProtocolStorage.getProtocolVersion(networkManager.l);
					networkManager.l = new InetSocketAddress(split[1], ((InetSocketAddress) networkManager.getSocketAddress()).getPort());
					ProtocolStorage.setProtocolVersion(networkManager.l, version);
					networkManager.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
					if (split.length == 4) {
						networkManager.spoofedProfile = HandshakeListener.gson.fromJson(split[3], Property[].class);
					}
				}
				((LoginListener) networkManager.getPacketListener()).hostname = packethandshakinginsetprotocol.b + ":" + packethandshakinginsetprotocol.c;
				break;
			}
			case STATUS: {
				networkManager.a(EnumProtocol.STATUS);
				networkManager.a(new PacketStatusListener(MinecraftServer.getServer(), networkManager));
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

}
