package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.Utils;

public class WindowCache {

	private WindowType windowType = WindowType.PLAYER;
	private int windowId = 0;
	private int windowSlots = 46;
	private int horseId = -1;

	public void setOpenedWindow(WindowType windowType, int windowId, int slots, int horseId) {
		this.windowType = windowType;
		this.windowId = windowId;
		this.windowSlots = slots;
		this.horseId = horseId;
	}

	public WindowType getOpenedWindow() {
		return this.windowType;
	}

	public int getOpenedWindowId() {
		return this.windowId;
	}

	public int getOpenedWindowSlots() {
		return this.windowSlots;
	}

	public int getHorseId() {
		return horseId;
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
		this.windowId = 0;
		this.windowSlots = 46;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
