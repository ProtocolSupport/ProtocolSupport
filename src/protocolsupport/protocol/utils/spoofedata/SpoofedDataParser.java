package protocolsupport.protocol.utils.spoofedata;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpoofedDataParser {

	private static final List<Function<String, SpoofedData>> parsers = new ArrayList<>();

	static {
		parsers.add(new BungeeCordSpoofedDataParser());
	}

	public static SpoofedData tryParse(String data) {
		return parsers.stream().map(parser -> {
			try {
				return parser.apply(data);
			} catch (Exception e) {
				return null;
			}
		}).filter(res -> res != null).findAny().orElse(null);
	}

}
