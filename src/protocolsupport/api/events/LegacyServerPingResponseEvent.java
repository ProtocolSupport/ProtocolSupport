package protocolsupport.api.events;

import java.net.InetSocketAddress;

@Deprecated
public class LegacyServerPingResponseEvent extends ServerPingResponseEvent {

	public LegacyServerPingResponseEvent(InetSocketAddress address, String motd, int maxPlayers) {
		super(address, null, null, motd, maxPlayers, null);
	}

}
