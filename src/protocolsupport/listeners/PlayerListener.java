package protocolsupport.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.ServerPlatform;

public class PlayerListener implements Listener {

	private final ProtocolSupport plugin;
	public PlayerListener(ProtocolSupport plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (
			player.isInsideVehicle() &&
			(connection != null) &&
			connection.getVersion().getProtocolType() == ProtocolType.PC &&
			connection.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)
		) {
			player.leaveVehicle();
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onVehicleInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (
			player.isInsideVehicle() &&
			(connection != null) &&
			(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
			connection.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)
		) {
			if (player.getVehicle().equals(event.getRightClicked())) {
				player.leaveVehicle();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Connection connection = ProtocolSupportAPI.getConnection(event.getPlayer());
		if (
			(connection != null) &&
			(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
			connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9)
		) {
			Block block = event.getBlock();
			connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockBreakSoundPacket(new Position(block.getX(), block.getY(), block.getZ()), block.getType()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onItemPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (
			(connection != null) &&
			(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
			connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9)
		) {
			player.playSound(
				event.getItem().getLocation(), Sound.ENTITY_ITEM_PICKUP,
				0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
			);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (!event.getVehicle().getPassengers().isEmpty()) {
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
				if ((header != null) || (footer != null)) {
					TabAPI.sendHeaderFooter(event.getPlayer(), header, footer);
				}
			}
		}, 1);
	}

}
