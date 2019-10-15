package protocolsupport.protocol.typeremapper.entity;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningEntityId {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REGISTRY = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(256);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("flatteningentityl.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIdRemappingTable table = REGISTRY.getTable(ProtocolVersion.valueOf(versionString));
			for (String entityidString : entriesObject.keySet()) {
				table.setRemap(Integer.parseInt(entityidString), JsonUtils.getInt(entriesObject, entityidString));
			}
		}
	}

}
