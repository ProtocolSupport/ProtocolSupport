package protocolsupport.protocol.typeremapper.legacy;

import java.text.MessageFormat;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;

public class LegacyDimension {

	public static String getStringId(NBTCompound dimension) {
		return dimension.getTagOfType("effects", NBTType.STRING).getValue();
	}

	public static int getIntId(NBTCompound dimension) {
		switch (dimension.getTagOfType("effects", NBTType.STRING).getValue()) {
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

	public static int getAlternativeIntId(int dimensionId) {
		return dimensionId != 0 ? 0 : -1;
	}

	public static String getWorldType(boolean flat) {
		return flat ? "flat" : "default";
	}

}
