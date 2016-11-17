package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.spigotmc.SpigotConfig;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EnumProtocol;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_11_R1.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ConnectionHandshakeEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ThrottleTracker;

public abstract class AbstractHandshakeListener implements PacketHandshakingInListener {

	private static final Gson gson = new Gson();

	protected final NetworkManager networkManager;

	public AbstractHandshakeListener(NetworkManager networkmanager) {
		this.networkManager = networkmanager;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void a(final PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN: {
				networkManager.setProtocol(EnumProtocol.LOGIN);
				//check connection throttle
				try {
					final InetAddress address = ((InetSocketAddress) networkManager.getSocketAddress()).getAddress();
					if (ThrottleTracker.isEnabled() && !SpigotConfig.bungee) {
						if (ThrottleTracker.throttle(address)) {
							final ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
							networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
								@Override
								public void operationComplete(Future<? super Void> arg0)  {
									networkManager.close(chatcomponenttext);
								}
							});
							return;
						}
					}
				} catch (Throwable t) {
					LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				//check client version (may be not latest if connection was from snapshot)
				ProtocolVersion clientversion = ProtocolVersion.fromId(packethandshakinginsetprotocol.b());
				if (clientversion != ProtocolVersion.getLatest()) {
					final ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedServerMessage.replaceAll("'", "''"), "1.10.2"));
					this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
						@Override
						public void operationComplete(Future<? super Void> arg0)  {
							networkManager.close(chatcomponenttext);
						}
					});
					break;
				}
				ConnectionImpl connection = ConnectionImpl.getFromChannel(networkManager.channel);
				//bungee spoofed data handling
				if (SpigotConfig.bungee) {
					final String[] split = packethandshakinginsetprotocol.hostname.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						final ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
						networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
							@Override
							public void operationComplete(Future<? super Void> arg0)  {
								networkManager.close(chatcomponenttext);
							}
						});
						return;
					}
					packethandshakinginsetprotocol.hostname = split[0];
					changeRemoteAddress(connection, new InetSocketAddress(split[1], connection.getAddress().getPort()));
					networkManager.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
					if (split.length == 4) {
						networkManager.spoofedProfile = gson.fromJson(split[3], Property[].class);
					}
				}
				//ps handshake event
				ConnectionHandshakeEvent event = new ConnectionHandshakeEvent(connection, packethandshakinginsetprotocol.hostname);
				Bukkit.getPluginManager().callEvent(event);
				if (event.getSpoofedAddress() != null) {
					changeRemoteAddress(connection, event.getSpoofedAddress());
				}
				//switch to login stage
				networkManager.setPacketListener(getLoginListener(networkManager, packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port));
				break;
			}
			case STATUS: {
				//switch to status stage
				networkManager.setProtocol(EnumProtocol.STATUS);
				networkManager.setPacketListener(new StatusListener(networkManager));
				break;
			}
			default: {
				throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
			}
		}
	}

	protected void changeRemoteAddress(ConnectionImpl connection, InetSocketAddress newRemote) {
		SocketAddress oldaddress = networkManager.getSocketAddress();
		ProtocolStorage.removeConnection(oldaddress);
		networkManager.l = newRemote;
		ProtocolStorage.setConnection(newRemote, connection);
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
	}

	public abstract AbstractLoginListener getLoginListener(NetworkManager networkManager, String hostname);

}
