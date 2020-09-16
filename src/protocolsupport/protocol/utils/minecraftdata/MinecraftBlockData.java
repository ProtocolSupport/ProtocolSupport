package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftBlockData {

	protected static final BlockDataEntry[] blocks = new BlockDataEntry[MinecraftData.BLOCKDATA_COUNT];

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MinecraftDataResourceUtils.getResourcePath("blocks.json"));
		for (String blockdataidString : rootObject.keySet()) {
			JsonObject blockdataObject = JsonUtils.getJsonObject(rootObject, blockdataidString);
			JsonObject soundsObject = JsonUtils.getJsonObject(blockdataObject, "sounds");
			blocks[Integer.parseInt(blockdataidString)] = new BlockDataEntry(
				MinecraftSoundData.getNameById(JsonUtils.getInt(soundsObject, "break")),
				JsonUtils.getFloat(soundsObject, "volume"),
				JsonUtils.getFloat(soundsObject, "pitch")
			);
		}
	}

	public static BlockDataEntry get(int blockdataId) {
		if ((blockdataId >= 0) && (blockdataId < blocks.length)) {
			return blocks[blockdataId];
		} else {
			return null;
		}
	}

	public static class BlockDataEntry {

		protected final String breakSound;
		protected final float volume;
		protected final float pitch;

		public BlockDataEntry(String breakSound, float volume, float pitch) {
			this.breakSound = breakSound;
			this.volume = volume;
			this.pitch = pitch;
		}

		public String getBreakSound() {
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
