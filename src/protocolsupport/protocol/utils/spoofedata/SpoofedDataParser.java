package protocolsupport.protocol.utils.spoofedata;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;

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

	public static SpoofedData tryParse(String data, boolean proxyEnabled) {
		return current.parse(data, proxyEnabled);
	}

	protected abstract SpoofedData parse(String data, boolean proxyEnabled);

}
