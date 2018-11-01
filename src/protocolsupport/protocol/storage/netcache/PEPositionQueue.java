package protocolsupport.protocol.storage.netcache;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;;
import protocolsupport.api.Connection;

import java.util.LinkedList;
import java.util.Queue;

public class PEPositionQueue {

	protected static Location _location = new Location(null, -1, -1, -1);
	protected Location location;
	protected Queue<Integer> awaitQueue = new LinkedList<>();

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @return {@code true} if position can be sent right now
	 */
	public boolean onPosition(double x, double y, double z, float pitch, float yaw) {
		if (location == null) {
			setup();
			return true;
		} else {
			location = new Location(null, x, y, z, yaw, pitch);
			System.out.println("!!! send move while prev not response");
		}
		return false;
	}

	public void setup() {
		location = _location;
	}

	public void onPositionLook(Connection conn) {
		if (location == null) {
			return;
		}

//        System.out.println("!!! MOVE RESPONSE");

		Location loc = location;
		location = null;

		if (loc == _location) {
			return;
		}

//        System.out.println("!!! RE SEND LATEST POSITION LOCATION");

		((CraftPlayer) conn.getPlayer()).getHandle().playerConnection.teleport(loc);
	}

	public Queue<Integer> getAwaitQueue() {
		return awaitQueue;
	}

	public void clear() {
		location = null;
	}

}
