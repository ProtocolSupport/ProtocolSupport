package protocolsupport.protocol.storage;

import protocolsupport.protocol.utils.types.WindowType;

public class SharedStorage {

	private double x;
	private double y;
	private double z;
	private int teleportConfirmId;

	public boolean isTeleportConfirmNeeded() {
		return teleportConfirmId != -1;
	}

	public int tryTeleportConfirm(double x, double y, double z) {
		if (
			(Double.doubleToLongBits(this.x) == Double.doubleToLongBits(x)) &&
			(Double.doubleToLongBits(this.y) == Double.doubleToLongBits(y)) &&
			(Double.doubleToLongBits(this.z) == Double.doubleToLongBits(z))
		) {
			int r = teleportConfirmId;
			teleportConfirmId = -1;
			return r;
		}
		return -1;
	}

	public void setTeleportLocation(double x, double y, double z, int teleportConfirmId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportConfirmId = teleportConfirmId;
	}

	private WindowType windowType = WindowType.PLAYER;

	public void setOpenedWindow(WindowType windowType) {
		this.windowType = windowType;
	}

	public WindowType getOpenedWindow() {
		return this.windowType;
	}

	public void closeWindow() {
		this.windowType = WindowType.PLAYER;
	}

}
