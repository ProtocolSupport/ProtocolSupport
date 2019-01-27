package protocolsupport.protocol.storage.netcache;

import protocolsupport.utils.Utils;

public class MovementCache {

	protected static final double acceptableError = 0.1;

	protected double x;
	protected double y;
	protected double z;
	protected int teleportConfirmId;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public int teleportConfirm() {
		int id = teleportConfirmId;
		teleportConfirmId = -1;
		return id;
	}

	public int tryTeleportConfirm(double x, double y, double z) {
		if (teleportConfirmId == -1) {
			return -1;
		}
		if (
			(Math.abs(this.x - x) < acceptableError) &&
			(Math.abs(this.y - y) < acceptableError) &&
			(Math.abs(this.z - z) < acceptableError)
		) {
			return teleportConfirm();
		}
		return -1;
	}

	public void setTeleportLocation(double x, double y, double z, int teleportConfirmId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportConfirmId = teleportConfirmId;
	}


	private static final int peIncreasedLeniencyMillis = 1000;
	private double peClientX;
	private double peClientY;
	private double peClientZ;
	private long pePositionLeniencyIncreaseTimestamp;
	private double pePositionLeniency = 0.5;
	private boolean peLeftPaddleTurning = false;
	private boolean peRightPaddleTurning = false;

	public void setPEClientPosition(double x, double y, double z) {
		this.peClientX = x;
		this.peClientY = y;
		this.peClientZ = z;
	}

	public double getPEClientX() {
		return peClientX;
	}

	public double getPEClientY() {
		return peClientY;
	}

	public double getPEClientZ() {
		return peClientZ;
	}

	public void updatePEPositionLeniency(double currentPEClientY) {
		if (currentPEClientY > peClientY) {
			pePositionLeniency = 3;
			pePositionLeniencyIncreaseTimestamp = System.currentTimeMillis();
		} else if ((pePositionLeniency != 0.5) && ((System.currentTimeMillis() - pePositionLeniencyIncreaseTimestamp) > peIncreasedLeniencyMillis)) {
			pePositionLeniency = 0.5;
		}
	}

	public boolean isPEPositionAboveLeniency() {
		return (Math.abs(peClientX - x) > pePositionLeniency) || (Math.abs(peClientY - y) > pePositionLeniency) || (Math.abs(peClientZ - z) > pePositionLeniency);
	}

	public boolean isPELeftPaddleTurning() {
		return peLeftPaddleTurning;
	}

	public void setPELeftPaddleTurning(boolean peLeftPaddleTurning) {
		this.peLeftPaddleTurning = peLeftPaddleTurning;
	}

	public boolean isPERightPaddleTurning() {
		return peRightPaddleTurning;
	}

	public void setPERightPaddleTurning(boolean peRightPaddleTurning) {
		this.peRightPaddleTurning = peRightPaddleTurning;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
