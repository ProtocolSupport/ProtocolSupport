package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;

public class WindowCache {

	protected WindowType windowType = WindowType.PLAYER;

	public void setOpenedWindow(WindowType windowType) {
		this.windowType = windowType;
	}

	public WindowType getOpenedWindow(int windowId) {
		if (windowId == 0) {
			return WindowType.PLAYER;
		} else {
			return windowType;
		}
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
