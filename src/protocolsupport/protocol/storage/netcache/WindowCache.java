package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;

public class WindowCache {

	protected static final int WINDOW_ID_PLAYER = 0;

	protected WindowType windowType = WindowType.PLAYER;
	protected int windowId = WINDOW_ID_PLAYER;

	public void setOpenedWindow(int windowId, WindowType windowType) {
		this.windowId = windowId;
		this.windowType = windowType;
	}

	public WindowType getOpenedWindow(int windowId) {
		if (windowId == WINDOW_ID_PLAYER) {
			return WindowType.PLAYER;
		} else {
			return windowType;
		}
	}

	public boolean isValidWindowId(int windowId) {
		return windowId == WINDOW_ID_PLAYER || windowId == this.windowId;
	}

	public void closeWindow() {
		this.windowId = WINDOW_ID_PLAYER;
		this.windowType = WindowType.PLAYER;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
