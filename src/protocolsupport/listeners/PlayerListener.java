package protocolsupport.listeners;

import net.minecraft.server.v1_8_R1.NetworkManager;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import protocolsupport.protocol.DataStorage;
import protocolsupport.utils.Utils;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		NetworkManager nm = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.networkManager;
		DataStorage.setPlayer(Utils.getChannel(nm).remoteAddress(), event.getPlayer());
	}

}
