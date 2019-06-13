package protocolsupport.zplatform.impl.spigot.injector;

import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_14_R1.PlayerChunkMap;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerEntryInjectorMap;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		PlayerChunkMap chunkmap = ((CraftWorld) event.getWorld()).getHandle().getChunkProvider().playerChunkMap;
		try {
			ReflectionUtils.getField(PlayerChunkMap.class, "trackedEntities").set(chunkmap, new SpigotEntityTrackerEntryInjectorMap());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to inject entity tracker injector");
		}
	}

}
