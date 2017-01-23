package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

public class ConnectionHandshakeEvent extends ConnectionEvent {

	private final String hostname;

	public ConnectionHandshakeEvent(Connection connection, String hostname) {
		super(connection);
		this.hostname = hostname;
	}

	public String getHostname() {
		return hostname;
	}

	protected InetSocketAddress spoofedAddress;

	public InetSocketAddress getSpoofedAddress() {
		return spoofedAddress;
	}

	public void setSpoofedAddress(InetSocketAddress spoofedAddress) {
		this.spoofedAddress = spoofedAddress;
	}


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
