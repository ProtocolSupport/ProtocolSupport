package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

/**
 * This event is fired after receiving the client handshake (handshake packet)
 */
public class ConnectionHandshakeEvent extends ConnectionEvent {

	private final String hostname;

	public ConnectionHandshakeEvent(Connection connection, String hostname) {
		super(connection);
		this.hostname = hostname;
	}

	/**
	 * Returns the hostname to which client connects to
	 * @return hostname which player used when connecting to server
	 */
	public String getHostname() {
		return hostname;
	}

	protected InetSocketAddress spoofedAddress;

	/**
	 * Returns connection set spoofed address <br>
	 * Returns null by default
	 * @return spoofed address or null if not set
	 */
	public InetSocketAddress getSpoofedAddress() {
		return spoofedAddress;
	}

	/**
	 * Sets connection spoofed address
	 * @param spoofedAddress spoofed address
	 */
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
