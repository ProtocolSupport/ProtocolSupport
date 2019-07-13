package protocolsupport.protocol.utils.spoofedata;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Function;

import org.bukkit.Bukkit;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.reflect.TypeToken;

import protocolsupport.utils.Utils;

public class PaperSpoofedDataParser implements Function<String, SpoofedData> {

	protected static final Type properties_type = new TypeToken<Collection<ProfileProperty>>() {}.getType();

	@Override
	public SpoofedData apply(String hostname) {
		if (PlayerHandshakeEvent.getHandlerList().getRegisteredListeners().length != 0) {
			PlayerHandshakeEvent handshakeEvent = new PlayerHandshakeEvent(hostname, false);
			Bukkit.getPluginManager().callEvent(handshakeEvent);
			if (!handshakeEvent.isCancelled()) {
				if (handshakeEvent.isFailed()) {
					return new SpoofedData(handshakeEvent.getFailMessage());
				}
				return new SpoofedData(
					handshakeEvent.getServerHostname(),
					handshakeEvent.getSocketAddressHostname(),
					handshakeEvent.getUniqueId(),
					Utils.GSON.fromJson(handshakeEvent.getPropertiesJson(), properties_type)
				);
			}
		}
		return null;
	}

}
