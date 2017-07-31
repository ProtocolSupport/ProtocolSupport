package protocolsupport.zplatform.pe;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import protocolsupport.api.Connection;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.types.NetworkEntity;

public class MCPEPosTracker implements Runnable {

	private int id = -1;
	private final JavaPlugin protocolSupport;
	private Connection connection;
	private NetworkDataCache cache;
	
	public MCPEPosTracker(JavaPlugin protocolSupport, Connection connection, NetworkDataCache cache) {
		this.protocolSupport = protocolSupport;
		this.connection = connection;
		this.cache = cache;
	}
	
	@Override
	public void run() {
		if(connection != null && cache != null) {
			Player p = connection.getPlayer();
			if(p != null && !p.isDead()) {
				for (Entity e : p.getNearbyEntities(48, 128, 48)) {
					NetworkEntity n = cache.getWatchedEntity(e.getEntityId());
					if(n != null) {
						Vector currentLoc = e.getLocation().toVector();
						Vector lastLoc = n.getPosition();
						if(currentLoc.getX() != lastLoc.getX() || currentLoc.getY() != lastLoc.getY() || currentLoc.getZ() != lastLoc.getZ()) {
							cache.updateWatchedPosition(n.getId(), currentLoc);
						}
						if(e.getLocation().getYaw() != n.getYaw() || e.getLocation().getPitch() != n.getPitch()) {
							cache.updateWatchedRotation(n.getId(), (byte) e.getLocation().getYaw(), (byte) e.getLocation().getPitch());
						}
					}
				}
			}
		} else {
			protocolSupport.getServer().getScheduler().cancelTask(this.id);
		}
	}
	
	public void init() {
		this.id = protocolSupport.getServer().getScheduler().scheduleSyncRepeatingTask(protocolSupport, this, 0, 1);
	}

}
