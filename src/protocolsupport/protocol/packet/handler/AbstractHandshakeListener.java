package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ConnectionHandshakeEvent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ThrottleTracker;
import protocolsupport.protocol.utils.spoofedata.SpoofedData;
import protocolsupport.protocol.utils.spoofedata.SpoofedDataParser;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractHandshakeListener {

	protected final NetworkManagerWrapper networkManager;
	protected AbstractHandshakeListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	public void handleSetProtocol(NetworkState nextState, String hostname, int port) {
		switch (nextState) {
			case LOGIN: {
				networkManager.setProtocol(NetworkState.LOGIN);
				//check connection throttle
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
				//version check
				if (!connection.getVersion().isSupported()) {
					if (connection.getVersion().equals(ProtocolVersion.MINECRAFT_PE_LEGACY)) {
						disconnect(MessageFormat.format(ServerPlatform.get().getMiscUtils().getOutdatedClientMessage().replace("'", "''"), ServerPlatform.get().getMiscUtils().getVersionName()));
					} else {
						disconnect(MessageFormat.format(ServerPlatform.get().getMiscUtils().getOutdatedServerMessage().replace("'", "''"), ServerPlatform.get().getMiscUtils().getVersionName()));
					}
					break;
				}
				//ps handshake event
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
				//bungee spoofed data handling
				if (event.shouldParseHostname() && ServerPlatform.get().getMiscUtils().isProxyEnabled()) {
					SpoofedData sdata = SpoofedDataParser.tryParse(hostname);
					if (sdata == null) {
						disconnect("Ip forwarding is enabled but spoofed data can't be decoded or is missing");
						return;
					}
					if (sdata.isFailed()) {
						disconnect(sdata.getFailMessage());
						return;
					}
					hostname = sdata.getHostname();
					connection.changeAddress(new InetSocketAddress(sdata.getAddress(), connection.getAddress().getPort()));
					networkManager.setSpoofedProfile(sdata.getUUID(), sdata.getProperties());
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

	@SuppressWarnings("unchecked")
	protected void disconnect(String message) {
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), future -> networkManager.close(message));
	}

	protected abstract AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname);

	protected abstract AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager);

}
