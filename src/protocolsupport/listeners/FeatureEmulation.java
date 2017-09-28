package protocolsupport.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.ServerPlatform;

public class FeatureEmulation implements Listener {

	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (
			player.isInsideVehicle() &&
			(connection != null) &&
			(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
			connection.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)
		) {
			player.leaveVehicle();
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
	public void onEntityDamage(EntityDamageEvent event) {
		if (((event.getCause() == DamageCause.FIRE_TICK) || (event.getCause() == DamageCause.FIRE) || (event.getCause() == DamageCause.DROWNING))) {
			for (Player player : ServerPlatform.get().getMiscUtils().getNearbyPlayers(event.getEntity().getLocation(), 48, 128, 48)) {
				if (player != null) {
					Connection connection = ProtocolSupportAPI.getConnection(player);
					if (
						(connection != null) &&
						(connection.getVersion().getProtocolType() == ProtocolType.PC) &&
						connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_12)
					) {
						connection.sendPacket(ServerPlatform.get().getPacketFactory().createEntityStatusPacket(event.getEntity(), 2));
					}
				}
			}
		}
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), new Runnable() {
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
