package protocolsupport.listeners.emulation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

//TODO: move to network layer (because we can transform crouch action to leave boat now)
public class LeaveVehicleOnCrouchEmulation implements Listener {

	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (!player.isInsideVehicle()) {
			return;
		}
		Connection connection = ProtocolSupportAPI.getConnection(player);
		if (connection == null) {
			return;
		}
		ProtocolVersion version = connection.getVersion();
		if ((version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			player.leaveVehicle();
		}
	}

}
