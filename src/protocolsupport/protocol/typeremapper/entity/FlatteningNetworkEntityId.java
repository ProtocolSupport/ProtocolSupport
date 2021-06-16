package protocolsupport.protocol.typeremapper.entity;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ThrowingArrayBasedIntTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningNetworkEntityId {

	private FlatteningNetworkEntityId() {
	}

	public static final IntMappingRegistry<ThrowingArrayBasedIntTable> REGISTRY = new IntMappingRegistry<>() {
		@Override
		protected ThrowingArrayBasedIntTable createTable() {
			return new ThrowingArrayBasedIntTable(256);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningentity.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIntMappingTable table = REGISTRY.getTable(ProtocolVersion.valueOf(versionString));
			for (String entityidString : entriesObject.keySet()) {
				table.set(Integer.parseInt(entityidString), JsonUtils.getInt(entriesObject, entityidString));
			}
		}
	}

}
