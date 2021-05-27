package protocolsupport.protocol.typeremapper.block;

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
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			FlatteningBlockDataTable table = getTable(ProtocolVersion.valueOf(versionString));
			for (String blockdataidString : entriesObject.keySet()) {
				JsonObject entryObject = entriesObject.get(blockdataidString).getAsJsonObject();
				table.set(
					Integer.parseInt(blockdataidString),
					new FlatteningBlockDataEntry(JsonUtils.getInt(entryObject, "bdId"), JsonUtils.getInt(entryObject, "bId"))
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
