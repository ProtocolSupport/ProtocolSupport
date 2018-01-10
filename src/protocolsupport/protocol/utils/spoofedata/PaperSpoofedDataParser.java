package protocolsupport.protocol.utils.spoofedata;

import java.util.UUID;
import java.util.function.Function;

import org.bukkit.Bukkit;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;

import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.utils.Utils;

public class PaperSpoofedDataParser implements Function<String, SpoofedData> {

	@Override
	public SpoofedData apply(String hostname) {
		if (PlayerHandshakeEvent.getHandlerList().getRegisteredListeners().length != 0) {
			PlayerHandshakeEvent handshakeEvent = new PlayerHandshakeEvent(hostname, false);
			Bukkit.getPluginManager().callEvent(handshakeEvent);
			if (!handshakeEvent.isCancelled()) {
				if (handshakeEvent.isFailed()) {
					return new SpoofedData(handshakeEvent.getFailMessage());
				}
				String spoofedHostname = handshakeEvent.getServerHostname();
				String spoofedAddress = handshakeEvent.getSocketAddressHostname();
				UUID uuid = handshakeEvent.getUniqueId();
				ProfileProperty[] properties = Utils.GSON.fromJson(handshakeEvent.getPropertiesJson(), ProfileProperty[].class);
				return new SpoofedData(spoofedHostname, spoofedAddress, uuid, properties);
			}
		}
		return null;
	}

}
