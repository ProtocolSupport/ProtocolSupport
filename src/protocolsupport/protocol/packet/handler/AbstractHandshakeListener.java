package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.events.ConnectionHandshakeEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ThrottleTracker;
import protocolsupport.protocol.utils.spoofedata.SpoofedData;
import protocolsupport.protocol.utils.spoofedata.SpoofedDataParser;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public abstract class AbstractHandshakeListener {

	protected final NetworkManagerWrapper networkManager;
	protected AbstractHandshakeListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	@SuppressWarnings("unchecked")
	public void handleSetProtocol(NetworkState nextState, String hostname, int port) {
		switch (nextState) {
			case LOGIN: {
				networkManager.setProtocol(NetworkState.LOGIN);
				//check connection throttle
				try {
					final InetAddress address = networkManager.getAddress().getAddress();
					if (ThrottleTracker.isEnabled() && !ServerPlatform.get().getMiscUtils().isProxyEnabled()) {
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
				ConnectionImpl connection = ConnectionImpl.getFromChannel(networkManager.getChannel());
				//version check
				if (!connection.getVersion().isSupported()) {
					String message = MessageFormat.format(ServerPlatform.get().getMiscUtils().getOutdatedServerMessage().replace("'", "''"), ServerPlatform.get().getMiscUtils().getVersionName());
					this.networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), new GenericFutureListener<Future<? super Void>>() {
						@Override
						public void operationComplete(Future<? super Void> arg0)  {
							networkManager.close(message);
						}
					});
					break;
				}
				//bungee spoofed data handling
				if (ServerPlatform.get().getMiscUtils().isProxyEnabled()) {
					SpoofedData sdata = SpoofedDataParser.tryParse(hostname);
					if (sdata == null) {
						String message = "Ip forwarding is enabled but spoofed data can't be decoded or is missing";
						networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), new GenericFutureListener<Future<? super Void>>() {
							@Override
							public void operationComplete(Future<? super Void> arg0)  {
								networkManager.close(message);
							}
						});
						return;
					}
					hostname = sdata.getHostname();
					connection.changeAddress(new InetSocketAddress(sdata.getAddress(), connection.getAddress().getPort()));
					networkManager.setSpoofedProfile(sdata.getUUID(), sdata.getProperties());
				}
				//ps handshake event
				ConnectionHandshakeEvent event = new ConnectionHandshakeEvent(connection, hostname);
				Bukkit.getPluginManager().callEvent(event);
				if (event.getSpoofedAddress() != null) {
					connection.changeAddress(event.getSpoofedAddress());
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

	protected abstract AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname);

	protected abstract AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager);

}
