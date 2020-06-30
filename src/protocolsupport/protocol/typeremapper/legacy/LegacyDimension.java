package protocolsupport.protocol.typeremapper.legacy;

import java.text.MessageFormat;

public class LegacyDimension {

	public static int getId(String dimension) {
		switch (dimension) {
			case "overworld":
			case "minecraft:overworld": {
				return 0;
			}
			case "the_nether":
			case "minecraft:the_nether": {
				return -1;
			}
			case "the_end":
			case "minecraft:the_end": {
				return 1;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown dimension {0}", dimension));
			}
		}
	}

}
