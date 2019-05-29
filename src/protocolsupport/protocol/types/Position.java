package protocolsupport.protocol.types;

import org.bukkit.Location;
import org.bukkit.World;

import protocolsupport.utils.Utils;

public class Position {

	public static final Position ZERO = new Position(0, 0, 0);

	protected int x;
	protected int y;
	protected int z;

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public static Position fromBukkit(Location location) {
		return new Position(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	public void mod(int x, int y, int z) {
		modifyX(x);
		modifyY(y);
		modifyZ(z);
	}

	public void modifyX(int cnt) {
		x += cnt;
	}

	public void modifyY(int cnt) {
		y += cnt;
	}

	public void modifyZ(int cnt) {
		z += cnt;
	}

	public Location toBukkit(World world) {
		return new Location(world, x, y, z);
	}

	@Override
	public Position clone() {
		return new Position(x, y, z);
	}

	public int getLocalCoord() {
		return ((x & 0xF) << 12) | ((z & 0xF) << 8) | y;
	}

	public ChunkCoord getChunkCoord() {
		return new ChunkCoord(x >> 4, z >> 4);
	}

	public static Position fromLocal(ChunkCoord chunkCoord, int localCoord) {
		return new Position(
			(chunkCoord.getX() << 4) + ((localCoord >> 12) & 0xF),
			(localCoord & 0xFF),
			(chunkCoord.getZ() << 4) + ((localCoord >>  8) & 0xF)
		);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	@Override
	public int hashCode() {
		int code = 7;
		code = (47 * code) + x;
		code = (47 * code) + y;
		code = (47 * code) + z;
		return code;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position pos = (Position) o;
			return (x == pos.getX()) && (y == pos.getY()) && (z == pos.getZ());
		}
		return false;
	}

}
