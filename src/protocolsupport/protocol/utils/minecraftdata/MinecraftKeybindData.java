package protocolsupport.protocol.utils.minecraftdata;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftKeybindData {

	private MinecraftKeybindData() {
	}

	private static final HashMap<String, String> nameToKeyRepr = new HashMap<>();

	static {
		ResourceUtils.getAsBufferedReader(MinecraftDataResourceUtils.getResourcePath("keybinds")).lines()
		.filter(line -> !line.isEmpty())
		.forEach(line -> {
			String[] split = line.split("[:]");
			nameToKeyRepr.put(split[0], KeyEvent.getKeyText(Integer.parseInt(split[1])));
		});
	}

	public static @Nullable String getKey(@Nonnull String name) {
		return nameToKeyRepr.get(name);
	}

}
