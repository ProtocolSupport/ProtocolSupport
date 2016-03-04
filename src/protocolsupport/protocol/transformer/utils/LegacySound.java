package protocolsupport.protocol.transformer.utils;

import java.util.HashMap;

import net.minecraft.server.v1_9_R1.SoundEffect;

public class LegacySound {

	private static final HashMap<Integer, HashMap<String, String>> legacySounds = new HashMap<>();

	//return new sound if legacy not found, so launcher will print a warning with that new sound name
	public static String getSoundName(int category, int soundType) {
		String newSound = SoundEffect.a.b(SoundEffect.a.getId(soundType)).a();
		HashMap<String, String> categorySounds = legacySounds.get(category);
		if (categorySounds != null) {
			String legacySound = categorySounds.get(newSound);
			if (legacySound != null) {
				return legacySound;
			}
		}
		return newSound;
	}

	public static String getLegacySoundName(String soundName) {
		switch (soundName) {
			case "game.player.hurt.fall.big":
			case "game.neutral.hurt.fall.big":
			case "game.hostile.hurt.fall.big": {
				return "damage.fallbig";
			}
			case "game.player.hurt.fall.small":
			case "game.neutral.hurt.fall.small":
			case "game.hostile.hurt.fall.small": {
				return "damage.fallsmall";
			}
			case "game.player.hurt":
			case "game.player.die":
			case "game.neutral.hurt":
			case "game.neutral.die":
			case "game.hostile.hurt":
			case "game.hostile.die": {
				return "damage.hit";
			}
			case "game.player.swim":
			case "game.neutral.swim":
			case "game.hostile.swim": {
				return "liquid.swim";
			}
			case "game.player.swim.splash":
			case "game.neutral.swim.splash":
			case "game.hostile.swim.splash": {
				return "liquid.splash";
			}
			default: {
				return soundName;
			}
		}
	}

	private static void register(int category, String newSound, String legacySound) {
		legacySounds.putIfAbsent(category, new HashMap<String, String>());
		legacySounds.get(category).put(newSound, legacySound);
	}

	static {
		//TODO: sounds
	}

}
