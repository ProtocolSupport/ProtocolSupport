package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	protected final NetworkManagerWrapper networkmanager;
	public ConnectionImpl(NetworkManagerWrapper networkmanager) {
		this.networkmanager = networkmanager;
	}

	public NetworkManagerWrapper getNetworkManagerWrapper() {
		return networkmanager;
	}

	@Override
	public Object getNetworkManager() {
		return networkmanager.unwrap();
	}

	@Override
	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	@Override
	public InetSocketAddress getRawAddress() {
		return networkmanager.getRawAddress();
	}

	@Override
	public InetSocketAddress getAddress() {
		return networkmanager.getAddress();
	}

	@Override
	public void changeAddress(InetSocketAddress newRemote) {
		SocketAddress primaryaddr = networkmanager.getRawAddress();
		ProtocolStorage.addAddress(primaryaddr, newRemote);
		networkmanager.setAddress(newRemote);
	}

	@Override
	public Player getPlayer() {
		return networkmanager.getBukkitPlayer();
	}

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public boolean handlePacketSend(Object packet) {
		boolean canSend = true;
		for (PacketSendListener listener : sendListeners) {
			try {
				if (!listener.onPacketSending(packet)) {
					canSend = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet sending");
				t.printStackTrace();
			}
		}
		return canSend;
	}

	public boolean handlePacketReceive(Object packet) {
		boolean canReceive = true;
		for (PacketReceiveListener listener : receiveListeners) {
			try {
				if (!listener.onPacketReceiving(packet)) {
					canReceive = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet receiving");
				t.printStackTrace();
			}
		}
		return canReceive;
	}

}
