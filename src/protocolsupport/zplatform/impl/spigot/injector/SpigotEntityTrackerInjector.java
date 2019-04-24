package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_14_R1.WorldServer;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
//TODO: restore after implementing
//		wserver.getChunkProvider().playerChunkMap = new SpigotEntityTracker(wserver);
	}

}
