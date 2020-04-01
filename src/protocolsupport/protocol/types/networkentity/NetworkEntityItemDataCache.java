package protocolsupport.protocol.types.networkentity;

public class NetworkEntityItemDataCache extends NetworkEntityDataCache {

	protected boolean needsSpawn = true;
	protected double x;
	protected double y;
	protected double z;
	protected double motX;
	protected double motY;
	protected double motZ;

	public void setPositionAndMotion(double x, double y, double z, double motX, double motY, double motZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.motX = motX;
		this.motY = motY;
		this.motZ = motZ;
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

	public double getMotX() {
		return motX;
	}

	public double getMotY() {
		return motY;
	}

	public double getMotZ() {
		return motZ;
	}

}
