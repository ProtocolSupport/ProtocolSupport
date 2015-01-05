package protocolsupport.protocol.v_1_5;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_8_R1.ChatComponentText;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R1.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R1.PacketStatusListener;

import org.apache.logging.log4j.LogManager;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.spigotmc.SpigotConfig;

import protocolsupport.protocol.v_1_5.serverboundtransformer.PacketDecoder;

import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

public class HandshakeListener implements PacketHandshakingInListener {

	private static final Gson gson = new Gson();
	private static final HashMap<InetAddress, Long> throttleTracker = new HashMap<InetAddress, Long>();
	private static int throttleCounter = 0;

	private final NetworkManager networkManager;

	private final PacketDecoder decoder;

	public HandshakeListener(PacketDecoder decoder, final NetworkManager networkmanager) {
		networkManager = networkmanager;
		this.decoder = decoder;
	}

	@Override
	public void a(final PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN: {
				networkManager.a(EnumProtocol.LOGIN);
				try {
					final long currentTime = System.currentTimeMillis();
					final long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
					final InetAddress address = ((InetSocketAddress) networkManager.getSocketAddress()).getAddress();
					synchronized (HandshakeListener.throttleTracker) {
						if (HandshakeListener.throttleTracker.containsKey(address) && !"127.0.0.1".equals(address.getHostAddress()) && ((currentTime - HandshakeListener.throttleTracker.get(address)) < connectionThrottle)) {
							HandshakeListener.throttleTracker.put(address, currentTime);
							final ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
							networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
							networkManager.close(chatcomponenttext);
							return;
						}
						HandshakeListener.throttleTracker.put(address, currentTime);
						++HandshakeListener.throttleCounter;
						if (HandshakeListener.throttleCounter > 200) {
							HandshakeListener.throttleCounter = 0;
							final Iterator<Entry<InetAddress, Long>> iter = HandshakeListener.throttleTracker.entrySet().iterator();
							while (iter.hasNext()) {
								final Map.Entry<InetAddress, Long> entry = iter.next();
								if (entry.getValue() > connectionThrottle) {
									iter.remove();
								}
							}
						}
					}
				} catch (Throwable t) {
					LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				if (packethandshakinginsetprotocol.b() > 47) {
					final ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedServerMessage, "1.8"));
					networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
					networkManager.close(chatcomponenttext);
					break;
				}
				if (packethandshakinginsetprotocol.b() < 47) {
					final ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedClientMessage, "1.8"));
					networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
					networkManager.close(chatcomponenttext);
					break;
				}
				networkManager.a(new LoginListener(MinecraftServer.getServer(), decoder, networkManager));
				if (SpigotConfig.bungee) {
					final String[] split = packethandshakinginsetprotocol.b.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						final ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
						networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
						networkManager.close(chatcomponenttext);
						return;
					}
					packethandshakinginsetprotocol.b = split[0];
					networkManager.j = new InetSocketAddress(split[1], ((InetSocketAddress) networkManager.getSocketAddress()).getPort());
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
