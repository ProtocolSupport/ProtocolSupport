package protocolsupport.listeners.emulation;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.ServerPlatform;

public class UpdateHandSlotOnItemDropEmulation implements Listener {

	protected final Set<UUID> clickDrop = new HashSet<>();

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	protected void onWindowClick(InventoryClickEvent event) {
		switch (event.getAction()) {
			case DROP_ALL_CURSOR, DROP_ONE_CURSOR, DROP_ALL_SLOT, DROP_ONE_SLOT -> clickDrop.add(event.getWhoClicked().getUniqueId());
			default -> {}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	protected void onDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (clickDrop.remove(player.getUniqueId())) {
			return;
		}
		if (event.isCancelled()) {
			return;
		}
		ProtocolVersion version = ProtocolSupportAPI.getProtocolVersion(player);
		if ((version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_2)) {
			ServerPlatform.get().getMiscUtils().updatePlayerInventorySlot(player, player.getInventory().getHeldItemSlot());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	protected void onQuit(PlayerQuitEvent event) {
		clickDrop.remove(event.getPlayer().getUniqueId());
	}

}
