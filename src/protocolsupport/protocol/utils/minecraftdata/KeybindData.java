package protocolsupport.protocol.utils.minecraftdata;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class KeybindData {

	private static final HashMap<String, String> nameToKeyRepr = new HashMap<>();

	static {
		new BufferedReader(new InputStreamReader(MinecraftData.getDataResource("keybinds"), StandardCharsets.UTF_8)).lines()
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
