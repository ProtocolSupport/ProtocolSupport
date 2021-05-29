package protocolsupport.protocol.utils.authlib;

import java.util.UUID;

import javax.annotation.Nonnull;

public class UUIDTypeAdapter {

	private UUIDTypeAdapter() {
	}

	public static @Nonnull UUID fromString(@Nonnull String s) {
		return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
	}

}
