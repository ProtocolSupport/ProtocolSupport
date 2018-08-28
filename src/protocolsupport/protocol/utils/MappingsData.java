package protocolsupport.protocol.utils;

import protocolsupport.api.ProtocolVersion;

public class MappingsData {

	public static String getResourcePath(String name) {
		return "mappings/" + name;
	}

	public static String getFlatteningResoucePath(ProtocolVersion version, String name) {
		return getResourcePath("flattening/" + version.toString().toLowerCase() + "/" + name);
	}

}
