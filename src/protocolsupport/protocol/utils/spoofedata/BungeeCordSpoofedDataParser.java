package protocolsupport.protocol.utils.spoofedata;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Function;

import com.google.common.reflect.TypeToken;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.utils.authlib.UUIDTypeAdapter;
import protocolsupport.utils.Utils;

public class BungeeCordSpoofedDataParser implements Function<String, SpoofedData> {

	@SuppressWarnings("serial")
	protected static final Type properties_type = new TypeToken<Collection<ProfileProperty>>() {}.getType();

	@Override
	public SpoofedData apply(String data) {
		final String[] split = data.split("\u0000");
		if ((split.length != 3) && (split.length != 4)) {
			return null;
		}
		return new SpoofedData(
			split[0],
			split[1],
			UUIDTypeAdapter.fromString(split[2]),
			split.length == 4 ? Utils.GSON.fromJson(split[3], properties_type) : null
		);
	}

}
