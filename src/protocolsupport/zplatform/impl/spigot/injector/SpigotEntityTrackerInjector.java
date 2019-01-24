package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_13_R2.WorldServer;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerBlock;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		if (!Utils.isJavaPropertyTrue("no-alt-tracker")) {
			//this is needed to prevent de-sync for older PC versions
			wserver.tracker = new SpigotEntityTracker(wserver);
		} else {
			System.out.println("Disabled ProtocolSupport entity tracker");
		}
		wserver.addIWorldAccess(new SpigotEntityTrackerBlock(wserver));
	}

}
