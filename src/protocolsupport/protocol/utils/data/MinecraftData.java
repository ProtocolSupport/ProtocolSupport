package protocolsupport.protocol.utils.data;

import java.io.InputStream;

import protocolsupport.utils.Utils;

public class MinecraftData {

	public static final String NAMESPACE_PREFIX = "minecraft:";

	public static String addNamespacePrefix(String val) {
		return NAMESPACE_PREFIX + val;
	}

	public static InputStream getDataResouce(String name) {
		return Utils.getResource("data/" + name);
	}

}
