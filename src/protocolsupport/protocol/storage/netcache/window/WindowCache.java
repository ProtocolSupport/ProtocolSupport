package protocolsupport.protocol.storage.netcache.window;

import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.Utils;

public class WindowCache {

	protected static final byte WINDOW_ID_PLAYER = 0;

	protected WindowType windowType = WindowType.PLAYER;
	protected byte windowId = WINDOW_ID_PLAYER;

	protected WindowRemapper playerWindowRemapper;
	protected WindowRemapper windowRemapper;
	protected Object windowMetadata;

	public void setPlayerWindow(WindowRemapper playerWindowRemaper) {
		this.playerWindowRemapper = playerWindowRemaper;
		this.windowRemapper = playerWindowRemaper;
	}

	public void setOpenedWindow(byte windowId, WindowType windowType, WindowRemapper windowRemapper) {
		this.windowId = windowId;
		this.windowType = windowType;
		this.windowRemapper = windowRemapper;
		this.windowMetadata = windowRemapper.createWindowMetadata();
	}

	public WindowType getOpenedWindowType() {
		return windowType;
	}

	public boolean isValidWindowId(int windowId) {
		return windowId == this.windowId;
	}

	public WindowRemapper getPlayerWindowRemapper() {
		return playerWindowRemapper;
	}

	public WindowRemapper getOpenedWindowRemapper() {
		return windowRemapper;
	}

	public Object getOpenedWindowMetadata() {
		return windowMetadata;
	}

	public void closeWindow() {
		this.windowId = WINDOW_ID_PLAYER;
		this.windowType = WindowType.PLAYER;
		this.windowRemapper = playerWindowRemapper;
		this.windowMetadata = null;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
