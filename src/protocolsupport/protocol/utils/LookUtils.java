package protocolsupport.protocol.utils;

public class LookUtils {

	public static float toFloat(byte value) {
		return (value * 360F) / 256F;
	}

	public static byte toByte(float value) {
		return (byte) ((value * 256F) / 360F);
	}

}
