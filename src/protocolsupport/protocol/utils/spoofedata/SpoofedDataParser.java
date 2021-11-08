package protocolsupport.protocol.utils.spoofedata;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;

import protocolsupport.api.Connection;

public abstract class SpoofedDataParser {

	protected static final SpoofedDataParser current = selectParser();

	protected static final SpoofedDataParser selectParser() {
		BungeeCordSpoofedDataParser bungeecordparser = new BungeeCordSpoofedDataParser();
		try {
			Class.forName(PlayerHandshakeEvent.class.getName());
			return new PaperSpoofedDataParser(bungeecordparser);
		} catch (Throwable e) {
		}
		return bungeecordparser;
	}

	public static SpoofedData tryParse(Connection connecton, String data, boolean proxyEnabled) {
		return current.parse(connecton, data, proxyEnabled);
	}

	protected abstract SpoofedData parse(Connection connecton, String data, boolean proxyEnabled);

}
