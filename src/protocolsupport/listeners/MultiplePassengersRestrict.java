package protocolsupport.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class MultiplePassengersRestrict implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (!event.getVehicle().getPassengers().isEmpty() && needsRestrict()) {
			event.setCancelled(true);
		}
	}

	private static final ProtocolVersion[] singlePassengerVersions = ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8);
	private static boolean needsRestrict() {
		for (ProtocolVersion version : singlePassengerVersions) {
			if (ProtocolSupportAPI.isProtocolVersionEnabled(version)) {
				return true;
			}
		}
		return false;
	}

}
