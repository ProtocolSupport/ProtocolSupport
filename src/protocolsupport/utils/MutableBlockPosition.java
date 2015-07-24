package protocolsupport.utils;

import net.minecraft.server.v1_8_R3.BlockPosition;

public class MutableBlockPosition extends BlockPosition {

	private int x;
	private int y;
	private int z;

	public MutableBlockPosition(int x, int y, int z) {
		super(x, y, z);
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

	public void setCoords(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
