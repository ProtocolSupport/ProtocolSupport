package protocolsupport.protocol.storage.netcache.window;

import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.ReflectionUtils;

public class WindowCache {

	protected static final byte WINDOW_ID_PLAYER = 0;

	protected WindowType windowType = WindowType.PLAYER;
	protected byte windowId = WINDOW_ID_PLAYER;

	protected WindowRemapper playerWindowRemapper;
	protected Object playerWindowMetadata;

	protected WindowRemapper windowRemapper;
	protected Object windowMetadata;

	public void setPlayerWindow(WindowRemapper playerWindowRemaper) {
		this.playerWindowRemapper = playerWindowRemaper;
		this.playerWindowMetadata = playerWindowRemapper.createWindowMetadata();
		this.windowRemapper = playerWindowRemaper;
		this.windowMetadata = playerWindowMetadata;
	}

	public void setOpenedWindow(byte windowId, WindowType windowType, int windowSlots, WindowRemapper windowRemapper) {
		this.windowId = windowId;
		this.windowType = windowType;
		this.windowRemapper = windowRemapper;
		this.windowMetadata = windowRemapper.createWindowMetadata();
		if (playerWindowMetadata instanceof PlayerWindowMetadata) {
			((PlayerWindowMetadata) playerWindowMetadata).onWindowOpen(windowType, windowSlots, windowMetadata);
		}
	}

	public void closeWindow() {
		if (playerWindowMetadata instanceof PlayerWindowMetadata) {
			((PlayerWindowMetadata) playerWindowMetadata).onWindowClose();
		}
		this.windowId = WINDOW_ID_PLAYER;
		this.windowType = WindowType.PLAYER;
		this.windowRemapper = playerWindowRemapper;
		this.windowMetadata = playerWindowMetadata;
	}

	public boolean isValidWindowId(int windowId) {
		return windowId == this.windowId;
	}

	public Object getPlayerWindowMetadata() {
		return playerWindowMetadata;
	}

	public WindowRemapper getPlayerWindowRemapper() {
		return playerWindowRemapper;
	}

	public WindowType getOpenedWindowType() {
		return windowType;
	}

	public Object getOpenedWindowMetadata() {
		return windowMetadata;
	}

	public WindowRemapper getOpenedWindowRemapper() {
		return windowRemapper;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
