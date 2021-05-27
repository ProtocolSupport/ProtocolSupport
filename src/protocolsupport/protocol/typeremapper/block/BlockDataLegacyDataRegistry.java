package protocolsupport.protocol.typeremapper.block;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class BlockDataLegacyDataRegistry extends IntMappingRegistry<ArrayBasedIntMappingTable> {

	public static final BlockDataLegacyDataRegistry INSTANCE = new BlockDataLegacyDataRegistry();

	public BlockDataLegacyDataRegistry() {
		applyDefault();
	}

	public void applyDefault() {
		clear();

		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("legacyblockdata.json"));
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
		return new ArrayBasedIntMappingTable(MinecraftBlockData.BLOCKDATA_COUNT);
	}

}
