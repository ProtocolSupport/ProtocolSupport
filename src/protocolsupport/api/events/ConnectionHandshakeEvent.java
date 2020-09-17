package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

/**
 * This event is fired after receiving the client handshake (handshake packet)
 */
public class ConnectionHandshakeEvent extends CancellableLoginConnectionEvent {

	protected String hostname;
	protected boolean shouldParseHostname = true;

	public ConnectionHandshakeEvent(Connection connection, String hostname) {
		super(connection);
		this.hostname = hostname;
	}

	/**
	 * Returns the hostname to which client connects to<br>
	 * This data can contain spoofed data
	 * @return hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets hostname to which client connects to<br>
	 * This data can contain spoofed data
	 * @param hostname hostname
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Returns true if server should attempt hostname spoofed data parsing in case proxy is enabled <br>
	 * Returns true by default
	 * @return true if server should attempt hostname spoofed data parsing in case proxy is enabled
	 */
	public boolean shouldParseHostname() {
		return shouldParseHostname;
	}

	/**
	 * Disables server hostname spoofed data parsing for this connection
	 */
	public void disableParsingHostname() {
		this.shouldParseHostname = false;
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
