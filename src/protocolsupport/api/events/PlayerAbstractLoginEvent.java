package protocolsupport.api.events;

import protocolsupport.api.Connection;

public abstract class PlayerAbstractLoginEvent extends CancellableLoginConnectionEvent {

	protected PlayerAbstractLoginEvent(Connection connection, boolean async) {
		super(connection, async);
	}

	protected PlayerAbstractLoginEvent(Connection connection) {
		this(connection, true);
	}

}
