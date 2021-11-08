package protocolsupport.protocol.typeremapper.entity;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ThrowingArrayBasedIntTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningNetworkEntityIdRegistry extends IntMappingRegistry<ThrowingArrayBasedIntTable> {

	public static final IntMappingRegistry<ThrowingArrayBasedIntTable> INSTANCE = new FlatteningNetworkEntityIdRegistry();

	public FlatteningNetworkEntityIdRegistry() {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningentity.json"));
		for (Entry<String, JsonElement> rootEntry : rootObject.entrySet()) {
			ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			for (Entry<String, JsonElement> entityidEntry : rootEntry.getValue().getAsJsonObject().entrySet()) {
				table.set(Integer.parseInt(entityidEntry.getKey()), entityidEntry.getValue().getAsInt());
			}
		}
	}

	@Override
	protected ThrowingArrayBasedIntTable createTable() {
		return new ThrowingArrayBasedIntTable(256);
	}

}
