package protocolsupport.protocol.utils.minecraftdata;

import java.util.EnumMap;
import java.util.Map;

import org.bukkit.Material;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class BlockData {

	private static final Map<Material, BlockDataEntry> byMaterial = new EnumMap<>(Material.class);

	static {
		for (JsonElement element : ResourceUtils.getAsIterableJson(MinecraftData.getResourcePath("blocks.json"))) {
			JsonObject object = element.getAsJsonObject();
			JsonObject soundobject = JsonUtils.getJsonObject(object, "sounds");
			Material material = Material.getMaterial(JsonUtils.getString(object, "material"));
			BlockDataEntry entry = new BlockDataEntry(
				material,
				JsonUtils.getInt(soundobject, "break"), JsonUtils.getFloat(soundobject, "volume"), JsonUtils.getFloat(soundobject, "pitch")
			);
			byMaterial.put(material, entry);
		}
	}

	public static BlockDataEntry get(Material material) {
		return byMaterial.get(material);
	}

	public static class BlockDataEntry {

		protected final Material material;
		protected final int breakSound;
		protected final float volume;
		protected final float pitch;

		public BlockDataEntry(Material material, int breakSound, float volume, float pitch) {
			this.material = material;
			this.breakSound = breakSound;
			this.volume = volume;
			this.pitch = pitch;
		}

		public Material getMaterial() {
			return material;
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
