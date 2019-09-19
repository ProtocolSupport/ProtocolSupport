package protocolsupport.protocol.typeremapper.window;

import protocolsupport.protocol.types.WindowType;

public abstract class AbstractWindowsRemapper {

	public static final AbstractWindowsRemapper NOOP = new AbstractWindowsRemapper() {
		@Override
		public WindowRemapper get(WindowType type, int windowSlots) {
			return NoopWindowRemapper.INSTANCE;
		}
	};

	public abstract WindowRemapper get(WindowType type, int windowSlots);

}
