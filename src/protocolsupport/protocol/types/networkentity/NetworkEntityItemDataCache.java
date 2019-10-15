package protocolsupport.protocol.types.networkentity;

public class NetworkEntityItemDataCache extends NetworkEntityDataCache {

	protected boolean needsSpawn = true;
	protected double x;
	protected double y;
	protected double z;

	public void setSpawnCoords(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean needsSpawn() {
		return needsSpawn;
	}

	public void resetNeedsSpawn() {
		needsSpawn = false;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

}
