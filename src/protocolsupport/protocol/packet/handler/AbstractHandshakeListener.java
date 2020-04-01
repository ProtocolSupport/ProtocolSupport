package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import protocolsupport.api.events.ConnectionHandshakeEvent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ThrottleTracker;
import protocolsupport.protocol.utils.spoofedata.SpoofedData;
import protocolsupport.protocol.utils.spoofedata.SpoofedDataParser;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractHandshakeListener implements IPacketListener {

	protected final NetworkManagerWrapper networkManager;
	protected AbstractHandshakeListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	@Override
	public void destroy() {
	}

	public void handleSetProtocol(NetworkState nextState, String hostname, int port) {
		switch (nextState) {
			case LOGIN: {
				networkManager.setProtocol(NetworkState.LOGIN);
				try {
					final InetAddress address = networkManager.getAddress().getAddress();
					if (ThrottleTracker.isEnabled() && !ServerPlatform.get().getMiscUtils().isProxyEnabled()) {
						if (ThrottleTracker.throttle(address)) {
							disconnect("Connection throttled! Please wait before reconnecting.");
							return;
						}
					}
				} catch (Throwable t) {
					Bukkit.getLogger().log(Level.WARNING, "Failed to check connection throttle", t);
				}

				ConnectionImpl connection = ConnectionImpl.getFromChannel(networkManager.getChannel());
				if (!connection.getVersion().isSupported()) {
					disconnect(MessageFormat.format(ServerPlatform.get().getMiscUtils().getOutdatedServerMessage().replace("'", "''"), ServerPlatform.get().getMiscUtils().getVersionName()));
					break;
				}

				ConnectionHandshakeEvent event = new ConnectionHandshakeEvent(connection, hostname);
				Bukkit.getPluginManager().callEvent(event);
				if (event.getSpoofedAddress() != null) {
					connection.changeAddress(event.getSpoofedAddress());
				}
				if (event.isLoginDenied()) {
					disconnect(event.getDenyLoginMessage());
					return;
				}
				hostname = event.getHostname();
				boolean proxyEnabled = event.shouldParseHostname() && ServerPlatform.get().getMiscUtils().isProxyEnabled();
				SpoofedData sdata = SpoofedDataParser.tryParse(hostname, proxyEnabled);
				if (sdata.isFailed()) {
					disconnect(sdata.getFailMessage());
					return;
				}
				if (proxyEnabled) {
					String spoofedRemoteAddress = sdata.getAddress();
					if (spoofedRemoteAddress != null) {
						connection.changeAddress(new InetSocketAddress(sdata.getAddress(), connection.getAddress().getPort()));
					}
					networkManager.setSpoofedProfile(sdata.getUUID(), sdata.getProperties());
				}
				hostname = sdata.getHostname();

				networkManager.setPacketListener(getLoginListener(networkManager));
				break;
			}
			case STATUS: {
				networkManager.setProtocol(NetworkState.STATUS);
				networkManager.setPacketListener(getStatusListener(networkManager));
				break;
			}
			default: {
				throw new UnsupportedOperationException("Invalid intention " + nextState);
			}
		}

		if (hostname != null) {
			networkManager.setVirtualHost(InetSocketAddress.createUnresolved(hostname, port));
		} else {
			networkManager.setVirtualHost(InetSocketAddress.createUnresolved("127.0.0.1", port));
		}
	}

	@Override
	public void disconnect(String message) {
		try {
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), future -> networkManager.close(message));
		} catch (Throwable exception) {
			networkManager.close("Error whilst disconnecting player, force closing connection");
		}
	}

	protected abstract AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager);

	protected abstract AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager);

}
