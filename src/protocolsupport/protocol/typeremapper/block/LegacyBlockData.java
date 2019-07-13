package protocolsupport.protocol.typeremapper.block;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyBlockData {

	public static final BlockIdRemappingRegistry REGISTRY = new BlockIdRemappingRegistry();

	public static class BlockIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {

		public BlockIdRemappingRegistry() {
			applyDefaultRemaps();
		}

		public void applyDefaultRemaps() {
			clear();

			JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("legacyblockdata.json"));
			for (String versionString : rootObject.keySet()) {
				JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
				ArrayBasedIdRemappingTable table = getTable(ProtocolVersion.valueOf(versionString));
				for (String blockdataidString : entriesObject.keySet()) {
					table.setRemap(Integer.parseInt(blockdataidString), JsonUtils.getInt(entriesObject, blockdataidString));
				}
			}
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.BLOCKDATA_COUNT);
		}

	}

}
