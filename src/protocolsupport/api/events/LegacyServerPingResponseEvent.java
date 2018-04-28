package protocolsupport.api.events;

import java.net.InetSocketAddress;

import protocolsupport.api.Connection;

/**
 * This event is no longer fired
 * @deprecated Use {@link ServerPingResponseEvent} instead
 */
@Deprecated
public class LegacyServerPingResponseEvent extends ServerPingResponseEvent {

	public LegacyServerPingResponseEvent(Connection connection, String motd, int maxPlayers) {
		super(connection, null, null, motd, maxPlayers, null);
	}

	@Deprecated
	public LegacyServerPingResponseEvent(InetSocketAddress address, String motd, int maxPlayers) {
		super(address, null, null, motd, maxPlayers, null);
	}

}
