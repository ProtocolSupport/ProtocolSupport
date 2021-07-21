package protocolsupport.protocol.types.networkentity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import protocolsupport.protocol.utils.PrimitiveTypeUtils;
import protocolsupport.utils.ReflectionUtils;

public class NetworkEntityDataCache {

	protected boolean firstMeta = true;

	public void setFirstMeta() {
		this.firstMeta = true;
	}

	public boolean isFirstMeta() {
		return firstMeta;
	}

	public void unsetFirstMeta() {
		this.firstMeta = false;
	}


	protected double x;
	protected double y;
	protected double z;
	protected float pitch;
	protected float yaw;
	protected byte headYaw;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public byte getPitchB() {
		return PrimitiveTypeUtils.toAngleB(pitch);
	}

	public byte getYawB() {
		return PrimitiveTypeUtils.toAngleB(yaw);
	}

	public byte getHeadYaw() {
		return headYaw;
	}

	public void setLocation(double x, double y, double z, byte pitch, byte yaw) {
		setX(x);
		setY(y);
		setZ(z);
		setPitch(PrimitiveTypeUtils.fromAngleB(pitch));
		setYaw(PrimitiveTypeUtils.fromAngleB(yaw));
	}

	protected static long toFixedPointPos(double pos) {
		double fixedPos = pos * 4096D;
		long fixedPosLong = (long) fixedPos;
		return fixedPos < fixedPosLong ? fixedPosLong - 1L : fixedPosLong;
	}

	protected static double fromFixedPointPos(long fixedPos) {
		return fixedPos / 4096D;
	}

	public void addLocation(short fpX, short fpY, short fpZ) {
		this.x = fromFixedPointPos(toFixedPointPos(this.x) + fpX);
		this.y = fromFixedPointPos(toFixedPointPos(this.y) + fpY);
		this.z = fromFixedPointPos(toFixedPointPos(this.z) + fpZ);
	}

	public void setLook(byte pitch, byte yaw) {
		setPitch(PrimitiveTypeUtils.fromAngleB(pitch));
		setYaw(PrimitiveTypeUtils.fromAngleB(yaw));
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void addX(double x) {
		this.x += x;
	}

	public void addY(double y) {
		this.y += y;
	}

	public void addZ(double z) {
		this.z += z;
	}

	public void addPitch(float pitch) {
		this.pitch += pitch;
	}

	public void addYaw(float yaw) {
		this.yaw += yaw;
	}

	public void setHeadYaw(byte headYaw) {
		this.headYaw = headYaw;
	}

	private final Map<String, Object> data = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}

	public void setData(String key, Object value) {
		data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T computeData(String key, BiFunction<String, T, T> compute) {
		return (T) data.compute(key, (BiFunction<String, Object, Object>) compute);
	}

	@SuppressWarnings("unchecked")
	public <T> T computeDataIfAbsent(String key, Function<String, T> compute) {
		return (T) data.computeIfAbsent(key, compute);
	}

	@SuppressWarnings("unchecked")
	public <T> T removeData(String key) {
		return (T) data.remove(key);
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}