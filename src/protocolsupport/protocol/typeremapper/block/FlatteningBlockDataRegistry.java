package protocolsupport.protocol.typeremapper.block;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningBlockDataRegistry extends MappingRegistry<FlatteningBlockDataTable> {

	public static final FlatteningBlockDataRegistry INSTANCE = new FlatteningBlockDataRegistry();

	public FlatteningBlockDataRegistry() {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningblockdata.json"));
		for (Entry<String, JsonElement> rootEntry : rootObject.entrySet()) {
			FlatteningBlockDataTable table = getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			for (Entry<String, JsonElement> blockdataidEntry : rootEntry.getValue().getAsJsonObject().entrySet()) {
				JsonObject blockdataidObject = blockdataidEntry.getValue().getAsJsonObject();
				table.set(
					Integer.parseInt(blockdataidEntry.getKey()),
					new FlatteningBlockDataEntry(JsonUtils.getInt(blockdataidObject, "bdId"), JsonUtils.getInt(blockdataidObject, "bId"))
				);
			}
		}
	}

	@Override
	protected FlatteningBlockDataTable createTable() {
		return new FlatteningBlockDataTable();
	}

	public static class FlatteningBlockDataTable extends MappingTable {

		protected final FlatteningBlockDataEntry[] table = new FlatteningBlockDataEntry[MinecraftBlockData.BLOCKDATA_COUNT];

		public FlatteningBlockDataEntry get(int blockdataId) {
			return table[blockdataId];
		}

		public int getId(int blockdataId) {
			FlatteningBlockDataEntry entry = get(blockdataId);
			return entry != null ? entry.getBlockDataId() : blockdataId;
		}

		public void set(int blockdataId, FlatteningBlockDataEntry entry) {
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
