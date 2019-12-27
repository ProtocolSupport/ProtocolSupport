package protocolsupport.protocol.utils.spoofedata;

import java.lang.reflect.Type;
import java.util.Collection;

import org.bukkit.Bukkit;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;
import com.google.gson.reflect.TypeToken;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.JsonUtils;

public class PaperSpoofedDataParser extends SpoofedDataParser {

	protected final BungeeCordSpoofedDataParser bungeecordparser;
	public PaperSpoofedDataParser(BungeeCordSpoofedDataParser bungeecordparser) {
		this.bungeecordparser = bungeecordparser;
	}

	protected static final Type properties_type = new TypeToken<Collection<ProfileProperty>>() {}.getType();

	@Override
	protected SpoofedData parse(String data, boolean proxyEnabled) {
		if (PlayerHandshakeEvent.getHandlerList().getRegisteredListeners().length != 0) {
			PlayerHandshakeEvent handshakeEvent = new PlayerHandshakeEvent(data, !proxyEnabled);
			Bukkit.getPluginManager().callEvent(handshakeEvent);
			if (!handshakeEvent.isCancelled()) {
				if (handshakeEvent.isFailed()) {
					return SpoofedData.createFailed(handshakeEvent.getFailMessage());
				}
				return SpoofedData.create(
					handshakeEvent.getServerHostname(),
					handshakeEvent.getSocketAddressHostname(),
					handshakeEvent.getUniqueId(),
					JsonUtils.GSON.fromJson(handshakeEvent.getPropertiesJson(), properties_type)
				);
			}
		}

		return bungeecordparser.parse(data, proxyEnabled);
	}

}
