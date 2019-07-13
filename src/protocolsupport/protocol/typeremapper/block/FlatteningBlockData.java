package protocolsupport.protocol.typeremapper.block;

import com.google.gson.JsonObject;

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

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("flatteningblockdata.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			FlatteningBlockDataTable table = REGISTRY.getTable(ProtocolVersion.valueOf(versionString));
			for (String blockdataidString : entriesObject.keySet()) {
				JsonObject entryObject = entriesObject.get(blockdataidString).getAsJsonObject();
				table.setRemap(
					Integer.parseInt(blockdataidString),
					new FlatteningBlockDataEntry(JsonUtils.getInt(entryObject, "bdId"), JsonUtils.getInt(entryObject, "bId"))
				);
			}
		}
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
