package protocolsupport.utils;

import net.minecraft.server.v1_8_R3.EntityPlayer;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Utils helper for SteerVehiclePacket
 * 
 * @author BeYkeRYkt
 */
public class SteerVehicleUtils {

	public static final float RADTODEG = 57.29577951F;
	public static int COMPRESSED_MIN = 0;
	public static int COMPRESSED_MAX = 128;

	public static int COMPRESSED_NORTH = 128;
	public static int COMPRESSED_NORTH_MIN = 95;
	public static int COMPRESSED_NORTH_MAX = -95;

	public static int COMPRESSED_EAST = -64;
	public static int COMPRESSED_EAST_MIN = -95;
	public static int COMPRESSED_EAST_MAX = -32;

	public static int COMPRESSED_SOUTH = 0;
	public static int COMPRESSED_SOUTH_MIN = -32;
	public static int COMPRESSED_SOUTH_MAX = 32;

	public static int COMPRESSED_WEST = 64;
	public static int COMPRESSED_WEST_MIN = 32;
	public static int COMPRESSED_WEST_MAX = 95;
	public static float moveForward = 0.98f;

	public static float getLookAtYaw(Vector motion) {
		return getLookAtYaw(motion.getX(), motion.getZ());
	}

	public static float getLookAtYaw(double dx, double dz) {
		return atan2(dz, dx) - 90f;
	}

	public static float atan2(double y, double x) {
		return RADTODEG * (float) Math.atan2(y, x);
	}

	public static float wrapAngle(float angle) {
		float wrappedAngle = angle;
		while (wrappedAngle <= -180f) {
			wrappedAngle += 360f;
		}
		while (wrappedAngle > 180f) {
			wrappedAngle -= 360f;
		}
		return wrappedAngle;
	}

	public static int getCompressedAngle(float value) {
		return (int) (value * 256.0F / 360.0F);
	}

	public static void applyVehicle(Player player, double dx, double dz) {
		if (player.getVehicle() != null) {
			EntityPlayer pl = ((CraftPlayer) player).getHandle();
			if (pl.ba != 0) {
				pl.ba = 0;
			}
			if (dx != 0 || dz != 0) {
				float f = getLookAtYaw(dx, dz);
				float wrappedYaw = getCompressedAngle(wrapAngle(f));
				float wrappedVehicleYaw = getCompressedAngle(wrapAngle(pl.yaw));
				float moveForward = getForwardValue(wrappedVehicleYaw, wrappedYaw);
				float moveStrafe = 0;//??
				pl.a(moveStrafe, moveForward, false, false);
			}
		}
	}

	public static float getForwardValue(float vehicleYaw, float yaw) {
		float value = 0;

		if (vehicleYaw >= COMPRESSED_EAST_MIN && vehicleYaw <= COMPRESSED_EAST_MAX) {// EAST
			if (yaw > COMPRESSED_WEST_MIN && yaw < COMPRESSED_WEST_MAX) { // WEST
				value = -moveForward;
			} else {
				value = moveForward;
			}
		} else if (vehicleYaw >= COMPRESSED_SOUTH_MIN && vehicleYaw <= COMPRESSED_SOUTH_MAX) { // SOUTH
			if (yaw >= COMPRESSED_NORTH_MIN && yaw <= 128 || yaw > -128 && yaw <= COMPRESSED_NORTH_MAX) {// NORTH
				value = -moveForward;
			} else {
				value = moveForward;
			}
		} else if (vehicleYaw > COMPRESSED_WEST_MIN && vehicleYaw < COMPRESSED_WEST_MAX) { // WEST
			if (yaw > COMPRESSED_EAST_MIN && yaw < COMPRESSED_EAST_MAX) { // EAST
				value = -moveForward;
			} else {
				value = moveForward;
			}
		} else if (vehicleYaw >= COMPRESSED_NORTH_MIN && vehicleYaw <= 128 || vehicleYaw > -128 && vehicleYaw <= COMPRESSED_NORTH_MAX) { // NORTH
			if (yaw >= COMPRESSED_SOUTH_MIN && yaw <= COMPRESSED_SOUTH_MAX) { // SOUTH
				value = -moveForward;
			} else {
				value = moveForward;
			}
		}
		return value;
	}
}
