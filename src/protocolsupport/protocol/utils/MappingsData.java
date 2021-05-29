package protocolsupport.protocol.utils;

import javax.annotation.Nonnull;

public class MappingsData {

	private MappingsData() {
	}

	public static @Nonnull String getResourcePath(@Nonnull String name) {
		return "mappings/" + name;
	}

}
