package protocolsupport.protocol.packet.middle;

public class CancelMiddlePacketException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final CancelMiddlePacketException INSTANCE = new CancelMiddlePacketException();

	private CancelMiddlePacketException() {
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}