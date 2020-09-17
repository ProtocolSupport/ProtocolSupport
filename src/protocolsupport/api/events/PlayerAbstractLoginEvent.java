package protocolsupport.api.events;

import protocolsupport.api.Connection;

public abstract class PlayerAbstractLoginEvent extends CancellableLoginConnectionEvent {

	public PlayerAbstractLoginEvent(Connection connection, boolean async) {
		super(connection, async);
	}

	public PlayerAbstractLoginEvent(Connection connection) {
		this(connection, true);
	}

}
