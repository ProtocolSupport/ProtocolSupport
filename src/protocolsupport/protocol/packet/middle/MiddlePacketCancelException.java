package protocolsupport.protocol.packet.middle;

public class MiddlePacketCancelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final MiddlePacketCancelException INSTANCE = new MiddlePacketCancelException();

	private MiddlePacketCancelException() {
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}