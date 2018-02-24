package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.WorldServer;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerBlock;

public class SpigotEntityTrackerInjector implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer wserver = ((CraftWorld) event.getWorld()).getHandle();
		wserver.tracker = new SpigotEntityTracker(wserver);
		wserver.addIWorldAccess(new SpigotEntityTrackerBlock(server, wserver));
	}

}
