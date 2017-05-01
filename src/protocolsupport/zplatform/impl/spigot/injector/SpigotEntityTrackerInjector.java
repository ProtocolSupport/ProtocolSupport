package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_11_R1.WorldServer;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		wserver.tracker = new SpigotEntityTracker(wserver);
	}

}
