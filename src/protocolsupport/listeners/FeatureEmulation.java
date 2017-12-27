package protocolsupport.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryOpen;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.typeremapper.pe.PEInventory.InvBlock;
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
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInventoryOpen(InventoryOpenEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			Connection connection = ProtocolSupportAPI.getConnection(player);
			if (
				(connection != null) &&
				(connection.getVersion().getProtocolType() == ProtocolType.PE)
			) {
				Location mainLoc = player.getLocation();
				//If the player is not on the ground or almost at bedrock, 
				//set the fake blocks above so the player doesn't fall on to it or so they aren't out of the world.
				if ((!player.isOnGround()) || (mainLoc.getBlockY() < 4)) {
					mainLoc.add(0, 6, 0);
				}
				connection.addMetadata("peInvBlocks", new InvBlock[] {
						new InvBlock(mainLoc.subtract(1, 2, 0).getBlock()), 
						new InvBlock(mainLoc.	  add(1, 0, 0).getBlock())
					});
				//Double chests need some ticks to confire after the inventory blocks are placed. We need to resend the inventory open.
				if (event.getView().getTopInventory().getSize() > 27) {
					Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), new Runnable() {
						@Override
						public void run() {
							if (connection.hasMetadata("smuggledWindowId")) {
								InventoryOpen.sendInventory(connection, 
									(int) connection.getMetadata("smuggledWindowId"),
									WindowType.CHEST, 
									Position.fromBukkit(mainLoc),
									-1
								);
								connection.removeMetadata("smuggledWindowId");
								player.updateInventory();
							}
						}
					}, 2);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent event) {
		if ((event.getClick() != ClickType.CREATIVE) && (event.getWhoClicked() instanceof Player)) {
			Player clicker = (Player) event.getWhoClicked();
			Connection connection = ProtocolSupportAPI.getConnection(clicker);
			if(
				(connection != null) &&
				(connection.getVersion().getProtocolType() == ProtocolType.PE) &&
				(
					(!connection.hasMetadata("lastScheduledInventoryUpdate")) ||
					(System.currentTimeMillis() - (long) connection.getMetadata("lastScheduledInventoryUpdate") >= 250)
				)
			) {
				connection.addMetadata("lastScheduledInventoryUpdate", System.currentTimeMillis());
				Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), new Runnable() {
					@Override
					public void run() {
						clicker.updateInventory();
						clicker.setItemOnCursor(event.getCursor());
					}
				}, 10);
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
