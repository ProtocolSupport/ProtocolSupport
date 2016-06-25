package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.spigotmc.SpigotConfig;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.HandshakeListener;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_10_R1.PacketLoginOutDisconnect;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ThrottleTracker;

public abstract class AbstractHandshakeListener extends HandshakeListener {

	private static final Gson gson = new Gson();

	protected final NetworkManager networkManager;

	@SuppressWarnings("deprecation")
	public AbstractHandshakeListener(NetworkManager networkmanager) {
		super(MinecraftServer.getServer(), networkmanager);
		this.networkManager = networkmanager;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void a(final PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN: {
				networkManager.setProtocol(EnumProtocol.LOGIN);
				try {
					final InetAddress address = ((InetSocketAddress) networkManager.getSocketAddress()).getAddress();
					if (ThrottleTracker.isEnabled() && !SpigotConfig.bungee) {
						if (ThrottleTracker.throttle(address)) {
							final ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
							networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
								@Override
								public void operationComplete(Future<? super Void> arg0) throws Exception {
									networkManager.close(chatcomponenttext);
								}
							});
							return;
						}
					}
				} catch (Throwable t) {
					LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				ProtocolVersion clientversion = ProtocolVersion.fromId(packethandshakinginsetprotocol.b());
				if (clientversion != ProtocolVersion.getLatest()) {
					final ChatComponentText chatcomponenttext = new ChatComponentText("Outdated server, max supported version: " + ProtocolVersion.getLatest());
					this.networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
						@Override
						public void operationComplete(Future<? super Void> arg0) throws Exception {
							networkManager.close(chatcomponenttext);
						}
					});
					break;
				}
				networkManager.setPacketListener(getLoginListener(networkManager));
				if (SpigotConfig.bungee) {
					final String[] split = packethandshakinginsetprotocol.hostname.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						final ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
						networkManager.sendPacket(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
							@Override
							public void operationComplete(Future<? super Void> arg0) throws Exception {
								networkManager.close(chatcomponenttext);
							}
						});
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
				((LoginListener) networkManager.i()).hostname = packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port;
				break;
			}
			case STATUS: {
				networkManager.setProtocol(EnumProtocol.STATUS);
				networkManager.setPacketListener(new StatusListener(MinecraftServer.getServer(), networkManager));
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
