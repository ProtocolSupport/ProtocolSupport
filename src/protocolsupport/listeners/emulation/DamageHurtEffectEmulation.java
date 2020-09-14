package protocolsupport.listeners.emulation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.ServerPlatform;

public class DamageHurtEffectEmulation implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {
		switch (event.getCause()) {
			case FIRE_TICK:
			case FIRE:
			case DROWNING: {
				for (Player player : ServerPlatform.get().getMiscUtils().getNearbyPlayers(event.getEntity().getLocation(), 48, 128, 48)) {
					if (player == null) {
						continue;
					}
					Connection connection = ProtocolSupportAPI.getConnection(player);
					if (connection == null) {
						return;
					}
					ProtocolVersion version = connection.getVersion();
					if ((version.getProtocolType() == ProtocolType.PC) && connection.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_12)) {
						connection.sendPacket(ServerPlatform.get().getPacketFactory().createEntityStatusPacket(event.getEntity(), 2));
					}
				}
				break;
			}
			default: {
				break;
			}
		}
	}

}
