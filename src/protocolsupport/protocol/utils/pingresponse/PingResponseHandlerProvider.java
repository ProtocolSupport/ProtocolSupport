package protocolsupport.protocol.utils.pingresponse;

import protocolsupportbuildprocessor.Preload;

@Preload
public class PingResponseHandlerProvider {

	private PingResponseHandlerProvider() {
	}

	private static final PingResponseHandler handler = createHandler();

	protected static PingResponseHandler createHandler() {
		try {
			return new PaperPingResponseHandler();
		} catch (Throwable t) {
		}
		return new SpigotPingResponseHandler();
	}

	public static PingResponseHandler get() {
		return handler;
	}

}
