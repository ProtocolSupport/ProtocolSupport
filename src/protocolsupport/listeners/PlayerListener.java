package protocolsupport.listeners;

import java.net.SocketAddress;

import net.minecraft.server.v1_8_R1.NetworkManager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.DataStorage;
import protocolsupport.utils.Utils;

public class PlayerListener implements Listener {

	private ProtocolSupport plugin;

	public PlayerListener(ProtocolSupport plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		NetworkManager nm = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.networkManager;
		DataStorage.setPlayer(Utils.getChannel(nm).remoteAddress(), event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuit(PlayerQuitEvent event) {
		final SocketAddress address = event.getPlayer().getAddress();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				DataStorage.clearData(address);
			}
		});
	}

}
