package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		//WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		//wserver.tracker = new SpigotEntityTracker(wserver);
		//wserver.addIWorldAccess(new SpigotEntityTrackerBlock(wserver));
//TODO: restore after implementing
//		wserver.getChunkProvider().playerChunkMap = new SpigotEntityTracker(wserver);
	}

}
