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
public class FlatteningNetworkEntityIdRegistry extends IntMappingRegistry<ThrowingArrayBasedIntTable> {

	public static final IntMappingRegistry<ThrowingArrayBasedIntTable> INSTANCE = new FlatteningNetworkEntityIdRegistry();

	public FlatteningNetworkEntityIdRegistry() {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningentity.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(versionString));
			for (String entityidString : entriesObject.keySet()) {
				table.set(Integer.parseInt(entityidString), JsonUtils.getInt(entriesObject, entityidString));
			}
		}
	}

	@Override
	protected ThrowingArrayBasedIntTable createTable() {
		return new ThrowingArrayBasedIntTable(256);
	}

}
