package protocolsupport.protocol.typeremapper.legacy;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.TranslationAPI;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public class LegacyI18NData {

	private static final EnumMap<ProtocolVersion, Set<String>> supported = new EnumMap<>(ProtocolVersion.class);
	static {
		load(ProtocolVersion.MINECRAFT_1_7_5);
		copy(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10);
		load(ProtocolVersion.MINECRAFT_1_8);
		load(ProtocolVersion.MINECRAFT_1_9);
		copy(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_9_1);
		copy(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_9_2);
		copy(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_9_4);
		load(ProtocolVersion.MINECRAFT_1_10);
		load(ProtocolVersion.MINECRAFT_1_11);
		copy(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_11_1);
		supported.put(ProtocolVersionsHelper.LATEST_PC, TranslationAPI.getTranslationKeys());
	}

	private static void load(ProtocolVersion version) {
		supported.put(version, I18NData.loadI18N("temp", Utils.getResource("i18n/legacy/" + version.toString())).getKeys());
	}

	private static void copy(ProtocolVersion from, ProtocolVersion to) {
		supported.put(to, new HashSet<>(supported.get(from)));
	}

	public static boolean isSupported(String translationKey, ProtocolVersion version) {
		return supported.getOrDefault(version, Collections.emptySet()).contains(translationKey);
	}

}
