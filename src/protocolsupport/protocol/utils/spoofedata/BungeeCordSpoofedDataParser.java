package protocolsupport.protocol.utils.spoofedata;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.common.reflect.TypeToken;

import protocolsupport.api.Connection;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.utils.authlib.UUIDTypeAdapter;
import protocolsupport.utils.JsonUtils;

public class BungeeCordSpoofedDataParser extends SpoofedDataParser {

	@SuppressWarnings("serial")
	protected static final Type properties_type = new TypeToken<Collection<ProfileProperty>>() {}.getType();

	@Override
	protected SpoofedData parse(Connection connection, String data, boolean proxyEnabled) {
		final String[] split = data.split("\u0000");
		if (proxyEnabled) {
			if ((split.length != 3) && (split.length != 4)) {
				return SpoofedData.createFailed(new TextComponent("Ip forwarding is enabled but spoofed data can't be decoded or is missing"));
			} else {
				return SpoofedData.create(split[0],
					split[1],
					UUIDTypeAdapter.fromString(split[2]),
					split.length == 4 ? JsonUtils.GSON.fromJson(split[3], properties_type) : null
				);
			}
		} else {
			return SpoofedData.createEmpty(split[0]);
		}
	}

}
