package protocolsupport.protocol.utils;

public class PrimitiveTypeUtils {

	public static byte toAngleB(float angleF) {
		return (byte) ((angleF * 256F) / 360F);
	}

	public static float fromAngleB(byte angleB) {
		return (angleB * 360F) / 256F;
	}

	public static int toFixedPoint32(double number) {
		return (int) (number * 32);
	}

	public static double fromFixedPoint32(int fpNumber) {
		return fpNumber / 32D;
	}

	public static int toFixedPoint8K(double number) {
		return (int) (number * 8000);
	}

	public static double fromFixedPoint8K(int fpNumber) {
		return fpNumber / 8000D;
	}

}
