package protocolsupport.listeners.emulation;

import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import protocolsupport.api.Connection;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData.BlockDataEntry;

public class BlockPlaceSelfSoundEmulation implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (connection == null) {
			return;
		}
		ProtocolVersion version = connection.getVersion();
		if ((version.getProtocolType() == ProtocolType.PC) &&version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			BlockDataEntry blockdataentry = MinecraftBlockData.get(MaterialAPI.getBlockDataNetworkId(event.getBlock().getBlockData()));
			player.playSound(
				event.getBlock().getLocation(),
				blockdataentry.getBreakSound(),
				SoundCategory.BLOCKS,
				(blockdataentry.getVolume() + 1.0F) / 2.0F,
				blockdataentry.getPitch() * 0.8F
			);
		}
	}

}
