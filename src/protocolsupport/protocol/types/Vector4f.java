package protocolsupport.protocol.types;

import protocolsupport.utils.reflection.ReflectionUtils;

public class Vector4f {

	protected float x;
	protected float y;
	protected float z;
	protected float w;

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getW() {
		return w;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void setW(float w) {
		this.w = w;
	}

	public void modifyX(float cnt) {
		x += cnt;
	}

	public void modifyY(float cnt) {
		y += cnt;
	}

	public void modifyZ(float cnt) {
		z += cnt;
	}

	public void modifyW(float cnt) {
		w += cnt;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
