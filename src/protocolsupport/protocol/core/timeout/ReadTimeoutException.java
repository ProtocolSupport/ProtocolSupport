package protocolsupport.protocol.core.timeout;

import net.minecraft.server.v1_8_R3.MinecraftServer;

public class ReadTimeoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private static final ReadTimeoutException notraceinstance = new ReadTimeoutException() {
		private static final long serialVersionUID = 1L;
		@Override
		public ReadTimeoutException fillInStackTrace() {
			return this;
		}
	};

	public static ReadTimeoutException getInstance() {
		return MinecraftServer.getServer().isDebugging() ? new ReadTimeoutException() : notraceinstance;
	}

	private ReadTimeoutException() {
	}

}
