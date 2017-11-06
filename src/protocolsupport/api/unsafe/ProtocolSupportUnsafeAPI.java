package protocolsupport.api.unsafe;

import java.math.BigInteger;

public class ProtocolSupportUnsafeAPI {

	private static final BigInteger apiversion = BigInteger.valueOf(3);
	/**
	 * Returns ProtocolSupport API version
	 * This number is incremented every time API changes (behavior change, method/field added/removed)
	 * Unsafe APIs are changed more frequently then normal ones, and may provide access to implementation-specific things which might now work everywhere
	 * @return API version
	 */
	public static BigInteger getAPIVersion() {
		return apiversion;
	}

}
