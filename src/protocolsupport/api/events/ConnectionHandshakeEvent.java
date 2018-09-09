package protocolsupport.api.events;

import java.net.InetSocketAddress;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;

/**
 * This event is fired after receiving the client handshake (handshake packet)
 */
public class ConnectionHandshakeEvent extends ConnectionEvent {

	protected String hostname;
	protected boolean shouldParseHostname = true;

	public ConnectionHandshakeEvent(Connection connection, String hostname) {
		super(connection);
		this.hostname = hostname;
	}

	/**
	 * Returns the hostname to which client connects to
	 * This data can contain spoofed data
	 * @return hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets hostname to which client connects to
	 * This data can contain spoofed data
	 * @param hostname hostname
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Returns true if server should attempt hostname spoofed data parsing in case proxy is enabled
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

	private String denyLoginMessage;

	/**
	 * Returns true if login is denied
	 * @return true if login is denied
	 */
	public boolean isLoginDenied() {
		return denyLoginMessage != null;
	}

	/**
	 * Returns deny login message or null if login is not denied
	 * @return deny login message or null
	 */
	public String getDenyLoginMessage() {
		return denyLoginMessage;
	}

	/**
	 * Sets the login deny message
	 * If message is null, login won't be denied
	 * @param message login deny message
	 */
	public void denyLogin(String message) {
		this.denyLoginMessage = message;
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
