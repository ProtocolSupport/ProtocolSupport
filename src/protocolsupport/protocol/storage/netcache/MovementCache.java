package protocolsupport.protocol.storage.netcache;

import protocolsupport.utils.Utils;

public class MovementCache {

	protected double x;
	protected double y;
	protected double z;
	protected int teleportConfirmId;

	public int tryTeleportConfirm(double x, double y, double z) {
		if (teleportConfirmId == -1) {
			return -1;
		}
		if ((this.x == x) && (this.y == y) && (this.z == z)) {
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

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
