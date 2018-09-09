package protocolsupport.api.events;

import protocolsupport.api.Connection;

public abstract class PlayerAbstractLoginEvent extends ConnectionEvent {

	public PlayerAbstractLoginEvent(Connection connection, boolean async) {
		super(connection, async);
	}

	public PlayerAbstractLoginEvent(Connection connection) {
		this(connection, true);
	}


	protected String denyLoginMessage;

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

}
