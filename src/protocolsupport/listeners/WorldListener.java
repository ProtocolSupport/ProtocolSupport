package protocolsupport.listeners;

import net.minecraft.server.v1_8_R1.World;

import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import protocolsupport.collections.TIntObjectBakedEntityList;

public class WorldListener implements Listener {

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		World nmsWorld = ((CraftWorld) event.getWorld()).getHandle();
		nmsWorld.entityList = new TIntObjectBakedEntityList(nmsWorld.entityList);
	}

}
