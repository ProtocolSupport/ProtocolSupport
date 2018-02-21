package protocolsupport.api.unsafe;

import java.math.BigInteger;

public class ProtocolSupportUnsafeAPI {

	private static final BigInteger apiversion = BigInteger.valueOf(5);
	/**
	 * Returns ProtocolSupport unsafe API version <br>
	 * This number is incremented every time API changes (behavior change, method/field added/removed) <br>
	 * Unsafe APIs are changed more frequently then normal ones (and without deprecation notice), and may provide access to implementation-specific things which might now work everywhere <br>
	 * @return unsafe API version
	 */
	public static BigInteger getAPIVersion() {
		return apiversion;
	}

}
