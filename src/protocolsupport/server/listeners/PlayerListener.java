package protocolsupport.server.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.tab.TabAPI;

public class PlayerListener implements Listener {

	private final ProtocolSupport plugin;
	public PlayerListener(ProtocolSupport plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (player.isInsideVehicle() && ProtocolSupportAPI.getProtocolVersion(player).isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			player.leaveVehicle();
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (player.isInsideVehicle() && ProtocolSupportAPI.getProtocolVersion(player).isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			if (player.getVehicle().equals(event.getRightClicked())) {
				player.leaveVehicle();
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (event.getVehicle().getPassenger() != null) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				BaseComponent header = TabAPI.getDefaultHeader();
				BaseComponent footer = TabAPI.getDefaultFooter();
				if (header != null || footer != null) {
					TabAPI.sendHeaderFooter(event.getPlayer(), header, footer);
				}
			}
		}, 1);
	}

}
