package protocolsupport.protocol.utils.types;

import protocolsupport.utils.Utils;

public class Position {

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

	public void modifyX(int cnt) {
		x += cnt;
	}

	public void modifyY(int cnt) {
		y += cnt;
	}

	public void modifyZ(int cnt) {
		z += cnt;
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
