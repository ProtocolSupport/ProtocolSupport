package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningBlockData {

	public static final RemappingRegistry<FlatteningBlockDataTable> REGISTRY = new RemappingRegistry<FlatteningBlockDataTable>() {
		@Override
		protected FlatteningBlockDataTable createTable() {
			return new FlatteningBlockDataTable();
		}
	};

	protected static void load(ProtocolVersion version) {
		JsonObject blockdataidJson = ResourceUtils.getAsJson(MappingsData.getFlatteningResoucePath(version, "blockdataid.json"));
		if (blockdataidJson != null) {
			JsonObject blockidJson = ResourceUtils.getAsJson(MappingsData.getFlatteningResoucePath(version, "blockid.json"));

			FlatteningBlockDataTable table = REGISTRY.getTable(version);
			for (Entry<String, JsonElement> entry : blockdataidJson.entrySet()) {
				String name = entry.getKey();
				int blockId = blockidJson.get(name).getAsJsonObject().get("protocol_id").getAsInt();
				for (JsonElement blockdataElement : JsonUtils.getJsonArray(entry.getValue().getAsJsonObject(), "states")) {
					JsonObject blockdataObject = blockdataElement.getAsJsonObject();
					String blockdata = name;
					if (blockdataObject.has("properties")) {
						blockdata +=
							"[" +
							blockdataObject.getAsJsonObject("properties").entrySet().stream()
							.map(bdEntry -> bdEntry.getKey() + "=" + bdEntry.getValue().getAsString())
							.collect(Collectors.joining(",")) +
							"]";
					}
					int blockdataId = JsonUtils.getInt(blockdataObject, "id");
					table.setRemap(MaterialAPI.getBlockDataNetworkId(Bukkit.createBlockData(blockdata)), new FlatteningBlockDataEntry(blockdataId, blockId));
				}
			}
		}
	}

	static {
		Arrays.stream(ProtocolVersion.getAllSupported()).forEach(FlatteningBlockData::load);
	}

	public static class FlatteningBlockDataTable extends RemappingTable {
		protected final FlatteningBlockDataEntry[] table = new FlatteningBlockDataEntry[MinecraftData.BLOCKDATA_COUNT];
		public FlatteningBlockDataEntry getRemap(int blockdataId) {
			return table[blockdataId];
		}
		public void setRemap(int blockdataId, FlatteningBlockDataEntry entry) {
			table[blockdataId] = entry;
		}
	}

	public static final class FlatteningBlockDataEntry {
		protected final int blockdataId;
		protected final int blockId;
		public FlatteningBlockDataEntry(int blockdataId, int blockId) {
			this.blockdataId = blockdataId;
			this.blockId = blockId;
		}
		public int getBlockDataId() {
			return blockdataId;
		}
		public int getBlockId() {
			return blockId;
		}
	}

}
