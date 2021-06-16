package protocolsupport.protocol.utils.minecraftdata;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftSoundData {

	private MinecraftSoundData() {
	}

	private static final String[] idToName = new String[2048];

	private static void register(@Nonnegative int id, @Nonnull String name) {
		idToName[id] = name;
	}

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MinecraftDataResourceUtils.getResourcePath("sounds.json"));
		for (String soundidString : rootObject.keySet()) {
			register(Integer.parseInt(soundidString), JsonUtils.getString(rootObject, soundidString));
		}
	}

	public static @Nullable String getNameById(@Nonnegative int id) {
		if ((id >= 0) && (id < idToName.length)) {
			return idToName[id];
		} else {
			return null;
		}
	}

}
