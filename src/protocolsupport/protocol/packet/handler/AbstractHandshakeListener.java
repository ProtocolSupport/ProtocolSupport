package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.google.gson.Gson;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ConnectionHandshakeEvent;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ThrottleTracker;
import protocolsupport.protocol.utils.authlib.UUIDTypeAdapter;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkState;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractHandshakeListener {

	private static final Gson gson = new Gson();

	protected final NetworkManagerWrapper networkManager;
	protected AbstractHandshakeListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void handleSetProtocol(int clientVersion, NetworkState nextState, String hostname, int port) {
		switch (nextState) {
			case LOGIN: {
				networkManager.setProtocol(NetworkState.LOGIN);
				//check connection throttle
				try {
					final InetAddress address = networkManager.getAddress().getAddress();
					if (ThrottleTracker.isEnabled() && !ServerPlatform.get().getMiscUtils().isBungeeEnabled()) {
						if (ThrottleTracker.throttle(address)) {
							String message = "Connection throttled! Please wait before reconnecting.";
							networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), new GenericFutureListener<Future<? super Void>>() {
								@Override
								public void operationComplete(Future<? super Void> arg0)  {
									networkManager.close(message);
								}
							});
							return;
						}
					}
				} catch (Throwable t) {
					Bukkit.getLogger().log(Level.WARNING, "Failed to check connection throttle", t);
				}
				//check client version (may be not latest if connection was from snapshot)
				ProtocolVersion clientversion = ProtocolVersion.fromId(clientVersion);
				if (clientversion != ProtocolVersion.getLatest()) {
					String message = MessageFormat.format(ServerPlatform.get().getMiscUtils().getOutdatedServerMessage().replace("'", "''"), ServerPlatform.get().getMiscUtils().getVersionName());
					this.networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), new GenericFutureListener<Future<? super Void>>() {
						@Override
						public void operationComplete(Future<? super Void> arg0)  {
							networkManager.close(message);
						}
					});
					break;
				}
				ConnectionImpl connection = ConnectionImpl.getFromChannel(networkManager.getChannel());
				//bungee spoofed data handling
				if (ServerPlatform.get().getMiscUtils().isBungeeEnabled()) {
					final String[] split = hostname.split("\u0000");
					if ((split.length != 3) && (split.length != 4)) {
						String message = "If you wish to use IP forwarding, please enable it in your BungeeCord config as well!";
						networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), new GenericFutureListener<Future<? super Void>>() {
							@Override
							public void operationComplete(Future<? super Void> arg0)  {
								networkManager.close(message);
							}
						});
						return;
					}
					hostname = split[0];
					changeRemoteAddress(connection, new InetSocketAddress(split[1], connection.getAddress().getPort()));
					networkManager.setSpoofedUUID(UUIDTypeAdapter.fromString(split[2]));
					if (split.length == 4) {
						networkManager.setSpoofedProperties(gson.fromJson(split[3], ProfileProperty[].class));
					}
				}
				//ps handshake event
				ConnectionHandshakeEvent event = new ConnectionHandshakeEvent(connection, hostname);
				Bukkit.getPluginManager().callEvent(event);
				if (event.getSpoofedAddress() != null) {
					changeRemoteAddress(connection, event.getSpoofedAddress());
				}
				//switch to login stage
				networkManager.setPacketListener(getLoginListener(networkManager, hostname + ":" + port));
				break;
			}
			case STATUS: {
				//switch to status stage
				networkManager.setProtocol(NetworkState.STATUS);
				networkManager.setPacketListener(getStatusListener(networkManager));
				break;
			}
			default: {
				throw new UnsupportedOperationException("Invalid intention " + nextState);
			}
		}
	}

	protected void changeRemoteAddress(ConnectionImpl connection, InetSocketAddress newRemote) {
		SocketAddress oldaddress = networkManager.getAddress();
		ProtocolStorage.removeConnection(oldaddress);
		networkManager.setAddress(newRemote);
		ProtocolStorage.setConnection(newRemote, connection);
	}

	protected abstract AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname);

	protected abstract AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager);

}
