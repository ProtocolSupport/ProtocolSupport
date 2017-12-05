package protocolsupport.zplatform.impl.paper.injector;

import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import protocolsupport.zplatform.impl.paper.entitytracker.PaperEntityTracker;

public class PaperEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		wserver.tracker = new PaperEntityTracker(wserver);
	}

}
