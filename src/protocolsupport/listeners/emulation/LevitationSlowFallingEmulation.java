package protocolsupport.listeners.emulation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class LevitationSlowFallingEmulation extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ProtocolVersion version = ProtocolSupportAPI.getProtocolVersion(player);
			if ((version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
				if (player.isFlying()) {
					continue;
				}
				if (
					player.hasPotionEffect(PotionEffectType.LEVITATION) ||
					(player.hasPotionEffect(PotionEffectType.SLOW_FALLING) && !isNearGround(player))
				) {
					player.setVelocity(player.getVelocity());
				}
			}
		}
	}

	protected static final double PLAYER_BB_SIZE = 0.3;

	protected static boolean isNearGround(Player player) {
		Location location = player.getLocation();
		World world = location.getWorld();
		double x = location.getX();
		double z = location.getZ();
		int y = location.getBlockY();
		return isGroundInAABB(world, x, z, y, PLAYER_BB_SIZE) || isGroundInAABB(world, x, z, y - 1, PLAYER_BB_SIZE);
	}

	protected static boolean isGroundInAABB(World world, double x, double z, int y, double offset) {
		if ((y < 0) || (y > world.getMaxHeight())) {
			return false;
		}
		if (!world.getBlockAt((int) (x + offset), y, (int) (z + offset)).isEmpty()) {
			return true;
		}
		if (!world.getBlockAt((int) (x - offset), y, (int) (z + offset)).isEmpty()) {
			return true;
		}
		if (!world.getBlockAt((int) (x + offset), y, (int) (z - offset)).isEmpty()) {
			return true;
		}
		if (!world.getBlockAt((int) (x - offset), y, (int) (z - offset)).isEmpty()) {
			return true;
		}
		return false;
	}

}
