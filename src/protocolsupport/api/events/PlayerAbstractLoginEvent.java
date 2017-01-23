package protocolsupport.api.events;

import protocolsupport.api.Connection;

public abstract class PlayerAbstractLoginEvent extends PlayerEvent {

	public PlayerAbstractLoginEvent(Connection connection, String username, boolean async) {
		super(connection, username, async);
	}

	public PlayerAbstractLoginEvent(Connection connection, String username) {
		this(connection, username, true);
	}

	private String denyLoginMessage;

	public boolean isLoginDenied() {
		return denyLoginMessage != null;
	}

	public String getDenyLoginMessage() {
		return denyLoginMessage;
	}

	public void denyLogin(String message) {
		this.denyLoginMessage = message;
	}

}
