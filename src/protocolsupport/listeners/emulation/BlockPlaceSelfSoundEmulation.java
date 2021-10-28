package protocolsupport.listeners.emulation;

import org.bukkit.SoundCategory;
import org.bukkit.SoundGroup;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class BlockPlaceSelfSoundEmulation implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (connection == null) {
			return;
		}
		ProtocolVersion version = connection.getVersion();
		if ((version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			SoundGroup soundgroup = event.getBlock().getBlockData().getSoundGroup();
			player.playSound(
				event.getBlock().getLocation(),
				soundgroup.getPlaceSound(),
				SoundCategory.BLOCKS,
				(soundgroup.getVolume() + 1.0F) / 2.0F,
				soundgroup.getPitch() * 0.8F
			);
		}
	}

}
