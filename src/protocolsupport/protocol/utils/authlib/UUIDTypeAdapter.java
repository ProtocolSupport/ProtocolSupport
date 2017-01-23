package protocolsupport.protocol.utils.authlib;

import java.util.UUID;

public class UUIDTypeAdapter {

	public static UUID fromString(final String s) {
		return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
	}

}
