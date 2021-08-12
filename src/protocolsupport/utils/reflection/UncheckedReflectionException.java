package protocolsupport.utils.reflection;

public class UncheckedReflectionException extends RuntimeException {

	private static final long serialVersionUID = 3789883196046843069L;

	public UncheckedReflectionException(Throwable t) {
		super(t);
	}

	public UncheckedReflectionException(String message) {
		super(message);
	}

	public UncheckedReflectionException(String message, Throwable t) {
		super(message, t);
	}

}