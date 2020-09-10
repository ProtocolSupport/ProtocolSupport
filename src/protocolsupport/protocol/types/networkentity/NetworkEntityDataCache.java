package protocolsupport.protocol.types.networkentity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import protocolsupport.protocol.utils.LookUtils;
import protocolsupport.utils.Utils;

public class NetworkEntityDataCache {

	protected boolean firstMeta = true;

	public boolean isFirstMeta() {
		return firstMeta;
	}

	public void unsetFirstMeta() {
		this.firstMeta = false;
	}

	protected static final double pos_s_to_real = 1 / 4096D;
	protected static final long pos_real_to_s = 4096L;

	protected long x;
	protected long y;
	protected long z;
	protected float pitch;
	protected float yaw;
	protected byte headYaw;

	public double getX() {
		return x * pos_s_to_real;
	}

	public double getY() {
		return y * pos_s_to_real;
	}

	public double getZ() {
		return z * pos_s_to_real;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public byte getPitchB() {
		return LookUtils.toByte(pitch);
	}

	public byte getYawB() {
		return LookUtils.toByte(yaw);
	}

	public byte getHeadYaw() {
		return headYaw;
	}

	public void setLocation(double x, double y, double z, byte pitch, byte yaw) {
		setX(x);
		setY(y);
		setZ(z);
		setPitch(LookUtils.toFloat(pitch));
		setYaw(LookUtils.toFloat(yaw));
	}

	public void addLocation(short sX, short sY, short sZ) {
		this.x += sX;
		this.y += sY;
		this.z += sZ;
	}

	public void setLook(byte pitch, byte yaw) {
		setPitch(LookUtils.toFloat(pitch));
		setYaw(LookUtils.toFloat(yaw));
	}

	public void setX(double x) {
		this.x = (long) (x * pos_real_to_s);
	}

	public void setY(double y) {
		this.y = (long) (y * pos_real_to_s);
	}

	public void setZ(double z) {
		this.z = (long) (z * pos_real_to_s);
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void addX(double x) {
		this.x += (long) (x * pos_real_to_s);
	}

	public void addY(double y) {
		this.y += (long) (y * pos_real_to_s);
	}

	public void addZ(double z) {
		this.z += (long) (z * pos_real_to_s);
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

	public void setData(String key, Object value) {
		data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T computeData(String key, BiFunction<String, T, T> compute) {
		return (T) data.compute(key, (BiFunction<String, Object, Object>) compute);
	}

	@SuppressWarnings("unchecked")
	public <T> T computeDataIfAbsent(String key, Function<String, T> compute) {
		return (T) data.computeIfAbsent(key, compute);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}