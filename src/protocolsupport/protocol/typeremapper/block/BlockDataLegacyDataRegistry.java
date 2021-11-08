package protocolsupport.protocol.typeremapper.block;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
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
		for (Entry<String, JsonElement> rootEntry : rootObject.entrySet()) {
			ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			for (Entry<String, JsonElement> blockdataidEntry : rootEntry.getValue().getAsJsonObject().entrySet()) {
				table.set(Integer.parseInt(blockdataidEntry.getKey()), blockdataidEntry.getValue().getAsInt());
			}
		}
	}

	@Override
	protected ArrayBasedIntMappingTable createTable() {
		return new ArrayBasedIntMappingTable(MinecraftBlockData.BLOCKDATA_COUNT);
	}

}
