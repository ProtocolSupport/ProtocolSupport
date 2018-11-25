package protocolsupport.protocol.utils.spoofedata;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;

public class SpoofedDataParser {

	private static final List<Function<String, SpoofedData>> parsers = new ArrayList<>();

	static {
		if (isPaperHandshakeEvent()) {
			parsers.add(new PaperSpoofedDataParser());
		}
		parsers.add(new BungeeCordSpoofedDataParser());
	}

	public static SpoofedData tryParse(String data) {
		for (Function<String, SpoofedData> parser : parsers) {
			try {
				SpoofedData result = parser.apply(data);
				if (result != null) {
					return result;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private static boolean isPaperHandshakeEvent() {
		try {
			Class.forName(PlayerHandshakeEvent.class.getName());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
