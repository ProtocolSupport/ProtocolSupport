package protocolsupport.protocol.typeremapper.block;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyBlockData {

	public static final BlockIdRemappingRegistry REGISTRY = new BlockIdRemappingRegistry();

	public static class BlockIdRemappingRegistry extends IntMappingRegistry<ArrayBasedIntMappingTable> {

		public BlockIdRemappingRegistry() {
			applyDefaultRemaps();
		}

		public void applyDefaultRemaps() {
			clear();

			JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("legacyblockdata.json"));
			for (String versionString : rootObject.keySet()) {
				JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
				ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(versionString));
				for (String blockdataidString : entriesObject.keySet()) {
					table.set(Integer.parseInt(blockdataidString), JsonUtils.getInt(entriesObject, blockdataidString));
				}
			}
		}

		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftData.BLOCKDATA_COUNT);
		}

	}

}
