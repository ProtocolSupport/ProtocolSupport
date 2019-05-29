package protocolsupport.api.chat.components;

import java.text.MessageFormat;

import protocolsupport.protocol.utils.minecraftdata.MinecraftKeybindData;

/**
 * Chat component that displays the client's current keybind for the specified key
 */
public class KeybindComponent extends BaseComponent {

	private final String keybind;
	public KeybindComponent(String keybind) {
		this.keybind = keybind;
	}

	public String getKeybind() {
		return keybind;
	}

	@Override
	public String getValue(String locale) {
		String keybindtext = MinecraftKeybindData.getKey(keybind);
		return MessageFormat.format("{0}({1})", keybind, keybindtext != null ? keybindtext : "unknown");
	}

}
