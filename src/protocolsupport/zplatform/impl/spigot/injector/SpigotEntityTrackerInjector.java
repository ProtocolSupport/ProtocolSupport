package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
//		PlayerChunkMap chunkmap = ((CraftWorld) event.getWorld()).getHandle().getChunkProvider().playerChunkMap;
//		try {
//			ReflectionUtils.getField(PlayerChunkMap.class, "trackedEntities").set(chunkmap, new SpigotEntityTrackerEntryInjectorMap());
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to inject entity tracker injector");
//		}
	}

}
