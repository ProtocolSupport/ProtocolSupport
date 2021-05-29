package protocolsupport.utils;

public class UnchekedReflectionException extends RuntimeException {

	private static final long serialVersionUID = 3789883196046843069L;

	public UnchekedReflectionException(Throwable t) {
		super(t);
	}

	public UnchekedReflectionException(String message) {
		super(message);
	}

	public UnchekedReflectionException(String message, Throwable t) {
		super(message, t);
	}

}