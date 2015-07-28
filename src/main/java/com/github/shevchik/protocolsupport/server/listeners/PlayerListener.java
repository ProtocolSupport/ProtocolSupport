package com.github.shevchik.protocolsupport.server.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.github.shevchik.protocolsupport.api.ProtocolSupportAPI;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;

public class PlayerListener implements Listener {

	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (player.isInsideVehicle() && ProtocolSupportAPI.getProtocolVersion(player) == ProtocolVersion.MINECRAFT_1_5_2) {
			player.leaveVehicle();
		}
	}

}
