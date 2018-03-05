package protocolsupport.protocol.utils.minecraftdata;

import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.utils.JsonUtils;

public class BlockData {

	private static final HashMap<String, BlockDataEntry> byName = new HashMap<>();
	private static final TIntObjectHashMap<BlockDataEntry> byId = new TIntObjectHashMap<>();

	static {
		read("blocks.json");
	}

	private static void read(String filename) {
		for (JsonElement element : MinecraftData.iterateJsonArrayResource(filename)) {
			JsonObject object = element.getAsJsonObject();
			JsonObject soundobject = JsonUtils.getJsonObject(object, "sounds");
			String name = JsonUtils.getString(object, "name");
			int runtimeId = JsonUtils.getInt(object, "id");
			BlockDataEntry entry = new BlockDataEntry(
				name, runtimeId,
				JsonUtils.getInt(soundobject, "break"), JsonUtils.getFloat(soundobject, "volume"), JsonUtils.getFloat(soundobject, "pitch")
			);
			byName.put(name, entry);
			byName.put(MinecraftData.addNamespacePrefix(name), entry);
			byId.put(runtimeId, entry);
		}
	}

	public static BlockDataEntry getByName(String name) {
		return byName.get(name);
	}

	public static BlockDataEntry getById(int runtimeId) {
		return byId.get(runtimeId);
	}

	public static class BlockDataEntry {
		protected final String name;
		protected final int runtimeId;
		protected final int breakSound;
		protected final float volume;
		protected final float pitch;

		public BlockDataEntry(String name, int runtimeId, int breakSound, float volume, float pitch) {
			this.name = name;
			this.runtimeId = runtimeId;
			this.breakSound = breakSound;
			this.volume = volume;
			this.pitch = pitch;
		}

		public String getName() {
			return name;
		}

		public int getRuntimeId() {
			return runtimeId;
		}

		public int getBreakSound() {
			return breakSound;
		}

		public float getVolume() {
			return volume;
		}

		public float getPitch() {
			return pitch;
		}
	}

}
