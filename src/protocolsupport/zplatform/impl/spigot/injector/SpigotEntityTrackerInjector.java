package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_13_R2.WorldServer;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		wserver.tracker = new SpigotEntityTracker(wserver);
	}

}
