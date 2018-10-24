package protocolsupport.protocol.utils.minecraftdata;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import protocolsupport.utils.Utils;

public class KeybindData {

	private static final HashMap<String, String> nameToKeyRepr = new HashMap<>();

	static {
		Utils.getResourceBuffered(MinecraftData.getResourcePath("keybinds")).lines()
		.filter(line -> !line.isEmpty())
		.forEach(line -> {
			String[] split = line.split("[:]");
			nameToKeyRepr.put(split[0], KeyEvent.getKeyText(Integer.parseInt(split[1])));
		});
	}

	public static String getKey(String name) {
		return nameToKeyRepr.get(name);
	}

}
